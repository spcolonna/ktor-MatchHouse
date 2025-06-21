package delivery.presenter

import com.example.delivery.dtos.GetFavouritesDto
import com.example.delivery.presenter.HousePresenter
import com.example.delivery.request.AddFavoriteRequest
import com.example.delivery.request.LoginUserRequest
import com.example.delivery.response.LoginUserResponse
import delivery.response.ResponseBuilder
import delivery.request.CreateUserRequest
import domain.useCases.favourites.AddFavouritesUseCase
import domain.useCases.favourites.GetListFavouriteUseCase
import domain.useCases.user.CreateUserUseCase
import domain.useCases.user.DeleteUserUseCase
import domain.useCases.user.GetUserUseCase
import domain.useCases.user.ModifyUserUseCase
import java.util.*

class UserPresenter(
    private val housePresenter: HousePresenter,
    private val createUserUserCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val modifyUserUseCase: ModifyUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val addFavouritesUseCase: AddFavouritesUseCase,
    private val getListFavouriteUseCase: GetListFavouriteUseCase
) {

    fun createUser(request: CreateUserRequest, responseBuilder: ResponseBuilder) {
        if(createUserUserCase.validate(request.mail))
            responseBuilder.onValid(createUserUserCase.execute(request.toDto()))
        else{
            responseBuilder.onValid(modifyUserUseCase.execute(request.toDto()))
        }
    }

    fun deleteUser(userId: String, responseBuilder: ResponseBuilder) {
        if(deleteUserUseCase.validateUser(userId)){
            responseBuilder.onValid(deleteUserUseCase.executeDelete(userId))
        }else{
            responseBuilder.onError()
        }
    }

    fun loginUser(body: LoginUserRequest, responseBuilder: ResponseBuilder) {
        val response = LoginUserResponse(
            id = UUID.randomUUID().toString(),
            mail = body.mail,
            token = "un-token-jwt-secreto-aqui" // En un futuro, aquí generarías un token real
        )
        responseBuilder.onValid(response)
    }

    fun getUser(id: String, responseBuilder: ResponseBuilder) =
        responseBuilder.onValid(getUserUseCase.execute(id))

    fun addFavourite(request: AddFavoriteRequest, responseBuilder: ResponseBuilder) {
        if(addFavouritesUseCase.validate(request.userId, request.houseId))
            responseBuilder.onValid(addFavouritesUseCase.execute(request.toDto()))
        else
            responseBuilder.onError()
    }

    fun getFavourites(userId: String, responseBuilder: ResponseBuilder) {
        val userFavourites = getListFavouriteUseCase.execute(userId)

        if(userFavourites.isEmpty()){
            responseBuilder.onValid(GetFavouritesDto(userId, listOf()))
        } else {
            responseBuilder.onValid(userFavourites.map {housePresenter.getHouseById(it)})
        }
    }

    fun deleteFavourite(request: AddFavoriteRequest, responseBuilder: ResponseBuilder) {
        TODO("Not yet implemented")
    }
}
