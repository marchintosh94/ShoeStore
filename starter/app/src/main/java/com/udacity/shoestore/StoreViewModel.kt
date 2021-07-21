package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.models.User
import timber.log.Timber

class StoreViewModel: ViewModel() {
    /*
    * User properties
    */
    private val _user: MutableLiveData<User> = MutableLiveData(User())
    val user: LiveData<User> get() = _user
    var userForm: MutableLiveData<User> = MutableLiveData(User())
    private val _eventLogin: MutableLiveData<Boolean> = MutableLiveData(false)
    val eventLogin: LiveData<Boolean>
        get() = _eventLogin

    /*
    * Shoes List properties
    */
    private val _shoes: MutableLiveData<MutableList<Shoe>> = MutableLiveData(
        mutableListOf<Shoe>(
            Shoe(
                "Air Jordan 1",
                42.0,
                "Nike",
                "The Air Jordan 1 Mid Shoe is inspired by the first AJ1, offering fans of Jordan retros a chance to follow in the footsteps of greatness. Fresh color trims the clean, classic materials, injecting some newness into the familiar design.",
                listOf(
                    "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/fb28a2df-c4a7-4c4e-a31e-10c00d6867d9/air-jordan-1-mid-shoe-nwV1GK.png",
                    "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5,q_80/223b1375-0512-437f-9910-20d7d88ed762/air-jordan-1-mid-shoe-nwV1GK.png"
                )
            ),
            Shoe(
                "Air Jordan 1 Mid",
                42.0,
                "Nike",
                "The Air Jordan 1 Mid Shoe is inspired by the first AJ1, offering fans of Jordan retros a chance to follow in the footsteps of greatness. Fresh color trims the clean, classic materials, injecting some newness into the familiar design.",
                listOf(
                    "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/fa5f46e6-0116-4af3-8fd5-94d94cab5e7a/air-jordan-1-mid-shoe-MVp2kJ.png"
                )
            ),
            Shoe(
                "Jordan Max Aura 2",
                42.0,
                "Nike",
                "The Jordan Max Aura 2 is inspired by the brand's rich legacy of performance basketball shoes. The design takes cues from past Air Jordans with lightweight cushioning and a look that's money on the street.",
                listOf(
                    "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/9004dae8-7cdd-4575-bc9a-9c2f4a004169/jordan-max-aura-2-mens-shoe-h9mJTc.png"
                )
            ),
            Shoe(
                "Air Jordan 1 Centre Court",
                42.0,
                "Nike",
                "The Air Jordan 1 Centre Court puts a sophisticated spin on one of sneaker history's most iconic designs. It's made with full-grain leather and suede in the upper, plus a double layer of cushioning under the heel. An exploded Wings logo stitched down along the back of the shoe adds distinction.",
                listOf(
                    "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5,q_80/27f68963-6218-4dad-a5ec-9fee2d31ebc1/air-jordan-1-centre-court-shoe-54L4sP.png",
                    "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5,q_80/e3304bd9-298b-4933-8018-b6f5d4cda239/air-jordan-1-centre-court-shoe-54L4sP.png",
                )
            ),
        )
    )
    val shoes : LiveData<MutableList<Shoe>> get() = _shoes
    private val _eventNavigateToList: MutableLiveData<Boolean> = MutableLiveData(false)
    val eventNavigateToList: LiveData<Boolean>
        get() = _eventNavigateToList

    /*
    * Detail form properties
    */
    var shoeDetail: MutableLiveData<Shoe> = MutableLiveData(Shoe(size = 42.0))
    var shoeSize: MutableLiveData<String> = MutableLiveData("")
    private val _errorMessage: MutableLiveData<StringBuilder> = MutableLiveData(java.lang.StringBuilder())
    val errorMessage: LiveData<StringBuilder> get() = _errorMessage

    override fun onCleared() {
        super.onCleared()
        Timber.i("Viewmodel cleared")
    }


    /*
     * User region
    */
    fun logout(){
        _user.value = User()
    }

    fun login(){
        if ( userForm.value != null && userForm.value!!.email != "" && userForm.value!!.password != ""){
            _user.value = userForm.value
            _eventLogin.value = true
            resetLoginForm()
        } else {
            _errorMessage.value = java.lang.StringBuilder("Email and password fields are required")
        }
    }

    private fun resetLoginForm(){
        _errorMessage.value = java.lang.StringBuilder()
        userForm.value = User()
    }

    fun onLoginSuccess(){
      _eventLogin.value = false
    }


    /*
     *   shoe detail region
    */
    fun validateShoe(){
        val valid: Boolean
        val message = java.lang.StringBuilder()
        when(""){
            shoeDetail.value?.name -> {
                valid = false
                message.append("Shoe name is required!")
            }
            shoeDetail.value?.company -> {
                valid = false
                message.append("Shoe company is required!")
            }
            shoeSize.value?.toString() -> {
                valid = false
                message.append("Shoe size is required!")
            }
            else -> {
                valid = true
            }
        }
        if (valid) {
            saveShoe()
        } else {
            _errorMessage.value = message
        }
    }

    fun saveShoe(){
        shoeDetail.value?.let { shoe ->
            shoe.size = shoeSize.value!!.toDouble()
            _shoes.value?.add(shoe)
            _eventNavigateToList.value = true
            resetShoeForm()
        }
    }

    fun onCancelDetailForm(){
        _eventNavigateToList.value = true
        resetShoeForm()
    }

    private fun resetShoeForm(){
        _errorMessage.value = java.lang.StringBuilder()
        shoeDetail = MutableLiveData(Shoe(size = 42.0))
        shoeSize = MutableLiveData("")
    }

    fun onNavigateToListComplete(){
        _eventNavigateToList.value = false
    }
}