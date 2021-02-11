package com.example.guru2_contestapp

import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ResumeActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var info: TextView
    lateinit var submitBtn: Button
    lateinit var editBtn: ImageButton
    lateinit var hopeET: EditText
    lateinit var selfIntroET: EditText
    lateinit var etcET: EditText
    lateinit var nameTextView: TextView
    lateinit var ageTextView: TextView
    lateinit var jobTextView: TextView

    lateinit var str_hope: String
    lateinit var str_self_intro: String
    lateinit var str_etc: String
    lateinit var str_name: String
    lateinit var str_year: String
    lateinit var str_job: String
    var t_num=0

    override fun onCreate(savedInstanceState: Bundle?) {

        // 로그인한 계정 아이디
        val sharedPreferences : SharedPreferences = this.getSharedPreferences("userid", AppCompatActivity.MODE_PRIVATE)
        var USER_ID = sharedPreferences.getString("USER_ID", "sorry")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        info=findViewById(R.id.WresumeInfoTextView)
        submitBtn=findViewById(R.id.WsubmitButton)
        hopeET=findViewById<EditText>(R.id.WwishPartEditText)
        selfIntroET=findViewById<EditText>(R.id.WselfIntroEditText)
        etcET=findViewById<EditText>(R.id.WetcEditText)
        nameTextView=findViewById(R.id.WnameTextView)
        ageTextView=findViewById(R.id.WageTextView)
        jobTextView=findViewById(R.id.WjobTextView)
        editBtn=findViewById(R.id.WprofileEditButton)

        // 상단 텍스트 뷰(공모전과 팀 이름) 내용을 이전 페이지에서 온 intent 값으로 설정
        val ic_name=intent.getStringExtra("intent_c_name")
        val it_name=intent.getStringExtra("intent_t_name")
        info.text=it_name+"("+ic_name+")"

        // DB에서 팀 이름 가지고 팀 번호를 찾아 t_num에 저장
        dbManager = DBManager(this, "ContestAppDB", null, 1)
        sqlitedb = dbManager.readableDatabase
        var cursor: Cursor
        cursor=sqlitedb.rawQuery("SELECT t_num FROM team WHERE t_name = '"+it_name+"';", null)
        if(cursor.moveToNext()){
            t_num=cursor.getInt(cursor.getColumnIndex("t_num"))
        }

        cursor=sqlitedb.rawQuery("SELECT m_name, m_year, m_job FROM member WHERE m_id = '"+USER_ID+"';", null)
        if(cursor.moveToNext()){
            str_name=cursor.getString(cursor.getColumnIndex("m_name"))
            str_year=cursor.getString(cursor.getColumnIndex("m_year"))
            str_job=cursor.getString(cursor.getColumnIndex("m_job"))
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        val this_year = Calendar.getInstance().get(Calendar.YEAR)
        var birth_year = 0
        if (str_year.toInt() > this_year){
            birth_year = ("19" + str_year).toInt()
        } else{
            birth_year = ("20" + str_year).toInt()
        }
        val age = this_year - birth_year + 1

        nameTextView.text=str_name
        ageTextView.text=age.toString()
        jobTextView.text=str_job

        // 프로필에서 수정 버튼 클릭 -> 수정 페이지로 이동
        editBtn.setOnClickListener {
            val intent= Intent(this, SettingActivity::class.java)
            startActivity(intent)

        }

        // 제출 버튼 클릭하면 입력 폼에 빈칸이 있는지 확인하고 있는 경우 대화상자로 알림
        // 빈칸 없는 경우 입력한 정보를 DB에 값을 입력하고 액티비티 종료
        submitBtn.setOnClickListener {
            val builder= AlertDialog.Builder(this)

            if(hopeET.text.toString()==""){
                builder.setMessage("희망 분야를 입력해 주세요.")
                //builder.setIcon(R.)
                builder.setPositiveButton("확인", null)
                builder.show()
            } else if (selfIntroET.text.toString()==""){
                builder.setMessage("자기소개를 해 주세요.")
                //builder.setIcon(R.)
                builder.setPositiveButton("확인", null)
                builder.show()
            } else{
                str_hope=hopeET.text.toString()
                str_self_intro=selfIntroET.text.toString()
                str_etc=etcET.text.toString()

                dbManager = DBManager(this, "ContestAppDB", null, 1)
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("INSERT INTO resume (m_id, t_num, r_hope, r_self_intro, r_etc) VALUES ('"+USER_ID+"', "+t_num+", '"+str_hope+"', '"+str_self_intro+"', '"+str_etc+"')")
                sqlitedb.execSQL("INSERT INTO teamManage (m_id, t_num, state) VALUES ('"+USER_ID+"', "+t_num+", 0)")
                sqlitedb.close()
                dbManager.close()

                this.finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        Log.i("--onResume--", "ok22")

        dbManager = DBManager(this, "ContestAppDB", null, 1)
        sqlitedb = dbManager.writableDatabase
        var cursor: Cursor
        cursor=sqlitedb.rawQuery("SELECT m_job FROM member WHERE m_name = '"+str_name+"';", null)
        if(cursor.moveToNext()){
            str_job=cursor.getString(cursor.getColumnIndex("m_job"))
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        jobTextView.text=str_job

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}