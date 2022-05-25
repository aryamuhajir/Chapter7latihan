package com.example.chapter7latihan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.chapter7latihan.R
import com.example.chapter7latihan.manager.UserManager
import com.example.chapter7latihan.model.GetAllUserResponseItem
import com.example.chapter7latihan.network.APIClient
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireContext())


        txtBlmPunyaAkun.setOnClickListener {
            view.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        btnLogin.setOnClickListener {
            var username = editUsername.text.toString()
            var password = editPassword.text.toString()
            if (username.isNotBlank() && password.isNotBlank()){
                login(username, password)
            }else{
                Toast.makeText(requireContext(), "Isi username dan password!", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun login(username : String, password : String){
        APIClient.instance.getUsers(username)
            .enqueue(object : Callback<List<GetAllUserResponseItem>>{
                override fun onResponse(
                    call: Call<List<GetAllUserResponseItem>>,
                    response: Response<List<GetAllUserResponseItem>>
                ) {
                    if (response.isSuccessful){
                        if(response.body() != null){
                            if (response.body()!![0].password == password){
                                GlobalScope.launch {
                                    val id = response.body()!![0].id
                                    userManager!!.setID(id)
                                }
                                view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
                            }else{
                                Toast.makeText(requireContext(), "Username atau password salah!", Toast.LENGTH_LONG).show()

                            }

                        }else{
                            Toast.makeText(requireContext(), "Username atau password salah!", Toast.LENGTH_LONG).show()

                        }
                    }
                }

                override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                }

            })


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}