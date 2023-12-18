package com.nayem.videocall
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // Set dark background color
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.dark)
                ) {
                    // Call your Compose function here
                    MyComposeUI()
                }
            }
        }
    }
}

@Composable
fun MyComposeUI() {
    var context = LocalContext.current
    // Your Compose UI code here
    // Use Column instead of LinearLayout for vertical stacking
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title at the top
        Text(
            text = "Start or Join a Meeting",
            fontSize = 26.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        // Image in the middle
        Image(
            painter = painterResource(id = R.drawable.onboarding),
            contentDescription = null, // Provide a content description
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(shape = MaterialTheme.shapes.medium)

        )
        Spacer(modifier = Modifier.size(50.dp))
        // Button at the bottom
        Button(
            onClick = {
                Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .height(60.dp)
                .width(300.dp)
                .padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue))
        ) {
            Text(text = "START", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyComposeUIPreview() {
    MyComposeUI()
}
