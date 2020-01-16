package org.sic4change.chuqabp.course.data.server

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object ChuqabpFirebaseService {
    val fbAuth = FirebaseAuth.getInstance()
    val mFirestore = FirebaseFirestore.getInstance()
    val mStorage = FirebaseStorage.getInstance()
    val caseDefault = "https://firebasestorage.googleapis.com/v0/b/chuqabp.appspot.com/o/cases%2Fcase_default.jpg?alt=media&token=02d9fbb0-dca5-433d-9a8b-563c902017c8"
}