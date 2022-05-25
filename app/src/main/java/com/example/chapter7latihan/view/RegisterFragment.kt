package com.example.chapter7latihan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.chapter7latihan.R
import com.example.chapter7latihan.model.GetAllUserResponseItem
import com.example.chapter7latihan.network.APIClient
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        btnDaftar.setOnClickListener {
            val username = editUsername1.text.toString()
            val password = editPassword1.text.toString()
            val password2 = editPassword2.text.toString()
            val name = editEmail1.text.toString()
            if (username.isNotBlank() && password.isNotBlank() && password2.isNotBlank() && name.isNotBlank()){
                if (password.equals(password2)){
                    APIClient.instance.register(name,username,password,"","","")
                        .enqueue(object : Callback<GetAllUserResponseItem> {
                            override fun onResponse(
                                call: Call<GetAllUserResponseItem>,
                                response: Response<GetAllUserResponseItem>
                            ) {
                                if (response.isSuccessful){
                                    Toast.makeText(requireContext(), "Berhasil terdaftar!", Toast.LENGTH_LONG).show()
                                    view.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
                                }else{

                                }
                            }

                            override fun onFailure(
                                call: Call<GetAllUserResponseItem>,
                                t: Throwable
                            ) {
                                Toast.makeText(requireContext(), "Terdapat kesalahan!", Toast.LENGTH_LONG).show()
                            }

                        })

                }else{
                    Toast.makeText(requireContext(), "Password dan Konfirmasi Password tidak sesuai", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(requireContext(), "Isi semua form!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}