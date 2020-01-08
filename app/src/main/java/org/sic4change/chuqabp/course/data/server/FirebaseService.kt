package org.sic4change.chuqabp.course.data.server

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object ChuqabpFirebaseService {
    val fbAuth = FirebaseAuth.getInstance()
    val mFirestore = FirebaseFirestore.getInstance()
    val mStorage = FirebaseStorage.getInstance()
    val caseDefault = "https://firebasestorage.googleapis.com/v0/b/chuqabp.appspot.com/o/cases%2Fdefault.jpg?alt=media&token=f629e336-3b34-46d9-a593-cd38f0d31b3e"
}