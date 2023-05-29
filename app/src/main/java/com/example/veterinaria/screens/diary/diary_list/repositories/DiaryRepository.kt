package com.example.veterinaria.screens.diary.diary_list.repositories

import com.example.veterinaria.modelos.Diary
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DiaryRepository
@Inject
constructor(
    @Named("diaryList") private val diaryList: CollectionReference
)
{
    // agregando nuevo documento a nuestra coleccion
    fun addNewDiary(diary: Diary) {
        try {
            //agrgando nuevo registro
            diaryList.document(diary.id).set(diary)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
    //emitir resultado
    fun getDiaryList() : Flow<Result<List<Diary>>> = flow {
        try {
            emit(Result.Loading<List<Diary>>())

            val diaryList = diaryList.get().await().map{ document ->
                document.toObject(Diary::class.java)
            }

            emit(Result.Success<List<Diary>>(data = diaryList))

        } catch (e: Exception) {
            emit(Result.Error<List<Diary>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun getDiaryById(diaryId: String): Flow<Result<Diary>> = flow {
        try {
            emit(Result.Loading<Diary>())

            val diary = diaryList
                .whereGreaterThanOrEqualTo("id", diaryId)
                .get()
                .await()
                .toObjects(Diary::class.java)
                .first()


            emit(Result.Success<Diary>(data = diary))

        } catch (e: Exception) {
            emit(Result.Error<Diary>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun updateDiary(diaryId: String, diary: Diary) {
        try {
            val map = mapOf(
                "pet" to diary.pet,
                "date" to diary.date,
                "hour" to diary.hour,
                "service" to diary.service,
            )

            diaryList.document(diaryId).update(map)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}