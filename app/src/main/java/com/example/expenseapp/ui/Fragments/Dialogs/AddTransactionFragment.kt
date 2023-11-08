package com.example.expenseapp.ui.Fragments.Dialogs

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.expenseapp.AccountsAdapter
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.databinding.BottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import com.example.expenseapp.R
import com.example.expenseapp.ViewModel.TransactionViewModel
import com.example.expenseapp.databinding.CategoryFragmentBinding
import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import com.example.expenseapp.helpers.ConvertDate
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat


class AddTransactionFragment : BottomSheetDialogFragment() {

    lateinit var binding: BottomDialogBinding
    lateinit var categoryBinding: CategoryFragmentBinding

    var addingTransaction = Transactions(null)

    lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = BottomDialogBinding.inflate(layoutInflater)
        categoryBinding = CategoryFragmentBinding.inflate(layoutInflater)

        EventBus.getDefault().register(this)
        initUI()
        return binding.root
    }

    private fun initUI() {

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        var incomeButton = binding.incomeToggle
        var expenseButton = binding.expenseToggle

        val buttons =
            listOf<Button>(
                binding.incomeToggle,
                binding.expenseToggle,
                binding.selectDateButton,
                binding.selectCategory,
                binding.accountCategory,
                binding.saveTransaction
            )

        buttons.forEach { tag ->
            tag.setOnClickListener {
                when (tag) {
                    binding.incomeToggle -> pressIncomeButton(tag, expenseButton)
                    binding.expenseToggle -> pressExpenseButton(incomeButton, tag)
                    binding.selectDateButton -> pressDateSelectionButton()
                    binding.selectCategory -> pressCategorySelectionButton()
                    binding.accountCategory -> pressAccountSelectionButton()
                    binding.saveTransaction -> pressSaveTransaction()
                }
            }

        }

    }

    private fun pressSaveTransaction() {
        addingTransaction.amount = binding.amountText.text.toString().toDouble()
        addingTransaction.note = binding.noteText.text.toString()
        addingTransaction.title = binding.noteText.text.toString()

        viewModel.addTransaction(addingTransaction)

        viewModel.transactionAdded.observe(viewLifecycleOwner) {

            if (it) {
                Toast.makeText(
                    requireContext(),
                    "Transaction details added succesfully",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(requireContext(), "Error occured", Toast.LENGTH_LONG).show()
            }

        }


    }


    private fun pressAccountSelectionButton() {
        var account = SelectAccountFragment()
        account.show(childFragmentManager, "Account")

        AccountsAdapter.accountSelection.clickData.observe(viewLifecycleOwner) {
            binding.accountCategory.text = it
            addingTransaction.account = it
        }

    }

    private fun pressCategorySelectionButton() {
        var category = SelectCategoryFragment()
        category.show(childFragmentManager, "Category") //shows the dialog framgnet
    }

    fun pressDateSelectionButton() {
        val myCalander = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalander.set(Calendar.YEAR, year)
            myCalander.set(Calendar.MONTH, month)
            myCalander.set(Calendar.DAY_OF_MONTH, day)

            updateDate(myCalander)
        }

        DatePickerDialog(
            requireContext(),
            datePicker, //values selected here will be saved under myCalander instance.......
            myCalander.get(Calendar.YEAR),
            myCalander.get(Calendar.MONTH),
            myCalander.get(Calendar.DAY_OF_MONTH)
        ).show()

//        addingTransaction.date = myCalander.time
        Log.d("Date",myCalander.time.toString())

    }

    fun pressIncomeButton(incomeButton: Button, expenseButton: Button) {

        incomeButton.backgroundTintList =
            context?.let { ContextCompat.getColorStateList(it, R.color.green) }
        incomeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.expenseToggle.backgroundTintList =
            context?.let { ContextCompat.getColorStateList(it, R.color.white) }
        expenseButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        addingTransaction.type = TransactionType.INCOME
    }

    fun pressExpenseButton(incomeButton: Button, expenseButton: Button) {

        expenseButton.backgroundTintList =
            context?.let { ContextCompat.getColorStateList(it, R.color.red) }
        expenseButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.incomeToggle.backgroundTintList =
            context?.let { ContextCompat.getColorStateList(it, R.color.white) }
        incomeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

        addingTransaction.type = TransactionType.EXPENSE

    }

    private fun updateDate(myCalander: Calendar) {
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy")
        binding.selectDateButton.text = dateFormat.format(myCalander.time)
        addingTransaction.date = ConvertDate.convertStringToDate(binding.selectDateButton.text.toString())!!
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCategorySelected(category: String) {
        Log.d("SecondActivity", "Received message:" + category + " \"")
        binding.selectCategory.text = category
        addingTransaction.category = Category.setEnumFromString(category.toString().toUpperCase())
    }


}