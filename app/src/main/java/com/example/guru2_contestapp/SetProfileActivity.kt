package com.example.guru2_contestapp

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SetProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_profile)

        //현재 로그인 중인 사용자 지정
        var context: Context = this
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("userid", AppCompatActivity.MODE_PRIVATE)
        var USER_ID = sharedPreferences.getString("USER_ID", "sorry")


        //사진 이름 저장 리스트
        var profileNameList: ArrayList<String> = ArrayList()
        for( i in 1..16){
            profileNameList.add("profile"+i.toString())
        }


        // 리사이클러뷰
        var profileItemList: ArrayList<ProfileItem> = ArrayList()


        try {
            // 여기 for문으로 해서 사진 넣을 예정
            profileItemList.add(ProfileItem(R.drawable.ic_baseline_account_circle_24))
            profileItemList.add(ProfileItem(R.drawable.ic_baseline_add_photo_alternate_24))
        }catch(e: Exception){
            Log.e("Error", e.message.toString())
        } finally{
        }


        // 리사이클러 뷰에 레이아웃 매니저와 어댑터 설정
        var rv_profile: RecyclerView = this.findViewById<RecyclerView>(R.id.rv_profile)
        rv_profile.layoutManager = GridLayoutManager(this, 2)
        rv_profile.setHasFixedSize(true)
        rv_profile.adapter = ProfileListAdapter(profileItemList)


    }

    //뒤로가기 설정
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}