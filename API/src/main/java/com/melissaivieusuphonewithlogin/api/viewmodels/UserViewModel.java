package com.melissaivieusuphonewithlogin.api.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.melissaivieusuphonewithlogin.api.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserViewModel extends ViewModel {
    FirebaseAuth auth;
    DatabaseReference database;
    MutableLiveData<User> user = new MutableLiveData<>();
    public UserViewModel() {
        this.auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser fbUser = auth.getCurrentUser();
        if (fbUser == null) {
            user.setValue(null);
        } else {
            user.setValue(new User(fbUser));
        }
        this.auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbUser = auth.getCurrentUser();
                if (fbUser == null) {
                    user.setValue(null);
                } else {
                    user.setValue(new User(fbUser));
                }
            }

        });

    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password);
//        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                AuthResult result = task.getResult();
//                if (result.getUser() == null) {
//                    loginError.setValue(new RuntimeException("Signup failed"));
//                }
//            }
//        });
    }

    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password);
    }

    public void signOut() {
        auth.signOut();
    }

    public void setUpMemoField(){
        database.child("userData").child(user.getValue().uid).child("memos").setValue(new ArrayList<String>());
    }

    public void storeUserSpecificData(String data) {
        DatabaseReference userData = database.child("userData").child(user.getValue().uid);
        String key = userData.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/memos/" + key, data);
        userData.updateChildren(childUpdates);
    }

    public List<String> getMemos(){
        List<String> memos = new ArrayList<String>();
        DatabaseReference userData = database.child("userData").child(user.getValue().uid);
        Task<DataSnapshot> reference = userData.child("memos").get();
        if(null != reference.getResult()){
            Iterable<DataSnapshot> children = reference.getResult().getChildren();
            children.forEach(child -> {memos.add(child.getValue().toString());});
        }
        return memos;
    }
}
