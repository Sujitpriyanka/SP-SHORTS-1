package com.spshorts.demo

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {
 private val yellow = Color.rgb(255,218,0)
 private val bg = Color.rgb(13,13,17)
 private lateinit var root: LinearLayout
 private var loggedIn = false
 private var displayName = "SP Creator"
 private var coins = 1200
 private var likes = 0
 private var following = false
 private var demoVideos = 0
 private var demoComments = 0
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); showLogin() }
 private fun init(title: String) {
  root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(bg); setPadding(20,28,20,16) }
  setContentView(root)
  text(title,25,true,yellow)
  spacer(14)
 }
 private fun text(value:String,size:Int=16,bold:Boolean=false,color:Int=Color.WHITE): TextView {
  val v=TextView(this).apply { text=value; textSize=size.toFloat(); setTextColor(color); if(bold) setTypeface(null,Typeface.BOLD); setPadding(4,8,4,8) }
  root.addView(v); return v
 }
 private fun spacer(h:Int) { root.addView(View(this),LinearLayout.LayoutParams(1,h)) }
 private fun button(label:String, action:()->Unit) {
  val b=Button(this).apply { text=label; isAllCaps=false; setTextColor(Color.BLACK); setBackgroundTintList(android.content.res.ColorStateList.valueOf(yellow)); setOnClickListener{action()} }
  root.addView(b,LinearLayout.LayoutParams(-1,-2)); spacer(7)
 }
 private fun field(hint:String):EditText { val e=EditText(this).apply { setSingleLine(); setTextColor(Color.WHITE); setHintTextColor(Color.LTGRAY); this.hint=hint };root.addView(e);return e }
 private fun nav() { spacer(15); button("Home / Discover") { showHome() }; button("ChatRoom") { showChat() }; button("Create") { showCreate() }; button("Wallet") { showWallet() }; button("Profile") { showProfile() } }
 private fun showLogin() {
  init("SP SHORTS"); spacer(24)
  val logo=ImageView(this).apply { setImageResource(com.spshorts.demo.R.drawable.sp_logo); scaleType=ImageView.ScaleType.FIT_CENTER }
  root.addView(logo,LinearLayout.LayoutParams(-1,230))
  text("Log in to Enter Your Profile",18,true)
  val phone=field("Phone number (demo)")
  button("Sign Up / Log in with Phone") { loggedIn=true; showHome() }
  button("Continue with Demo Account") { loggedIn=true; showHome() }
  text("Demo mode: no OTP, Google authentication or server connection.",12,false,Color.LTGRAY)
 }
 private fun showHome() {
  if(!loggedIn){showLogin();return}
  init("SP SHORTS  •  Discover")
  text("Discover     Follow     For You",17,true)
  spacer(30)
  text("▶",72,true,yellow).gravity=Gravity.CENTER
  text("Demo Shorts Feed",24,true).gravity=Gravity.CENTER
  text("Video playback will be connected in a later build.",14,false,Color.LTGRAY).gravity=Gravity.CENTER
  spacer(20)
  button("♥ Like  ($likes)") { likes++; showHome() }
  button("💬 Comments ($demoComments)") { showComments() }
  button(if(following) "✓ Following" else "+ Follow") { following=!following;showHome() }
  nav()
 }
 private fun showComments() { init("Comments"); text("Demo comments: $demoComments");val e=field("Write a comment");button("Post comment") { if(e.text.isNotBlank()) { demoComments++;showComments() } };button("Back") {showHome()} }
 private fun showCreate() { init("Create • Demo"); text("Video     Live     Chat",19,true);text("This screen currently simulates video creation; it does not record or upload media.",14);button("Add demo video to profile") { demoVideos++;toast("Demo video added") };button("Open profile") {showProfile()};nav() }
 private fun showChat() {init("ChatRoom");text("Common     Family",18,true);listOf("Live Broadcast","Creator Lounge","SP Shorts Family").forEach { name -> button(name) { showRoom(name) } };nav()}
 private fun showRoom(name:String) {init(name);text("Local demonstration room");val e=field("Message");button("Send demo message") {toast(if(e.text.isBlank()) "Enter a message" else "Demo message: ${e.text}")};button("Back to ChatRoom") {showChat()} }
 private fun showWallet() {init("Wallet • Demo");text("★ $coins Demo Coins",28,true,yellow);button("Recharge +500 demo coins") {coins+=500;showWallet()};button("Withdraw 100 demo coins") { if(coins>=100) {coins-=100;toast("Demo withdrawal recorded");showWallet()} else toast("Insufficient demo coins") };text("No real money, payment or payout occurs.",13,false,Color.LTGRAY);nav()}
 private fun showProfile() {init("My Profile");text(displayName,23,true);text("Followers  3     Following  ${if(following) 4 else 3}     Likes  $likes");text("Videos  $demoVideos");button("Edit Profile") {editProfile()};button("Creator Verification") {showVerification()};button("Settings") {showSettings()};nav()}
 private fun editProfile() {init("Edit Profile");val e=field("Display name");e.setText(displayName);button("Save") {displayName=e.text.toString().ifBlank {"SP Creator"};showProfile()}}
 private fun showVerification() {init("SP SHORTS • Creator Verification");text("Demo checklist",20,true);text("✓ Demo profile created\n○ Face verification (not connected)\n○ Real face photo and bio (not connected)\n○ 5 videos with 3000+ views (not connected)");button("Back to Profile") {showProfile()}}
 private fun showSettings() {init("Settings");button("Log out") {loggedIn=false;showLogin()};button("Back to Profile") {showProfile()}}
 private fun toast(s:String) {Toast.makeText(this,s,Toast.LENGTH_SHORT).show()}
 override fun onBackPressed() { if(loggedIn) showHome() else super.onBackPressed() }
}
