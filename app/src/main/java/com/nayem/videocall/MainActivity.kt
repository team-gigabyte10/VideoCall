package com.nayem.videocall
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.call.controls.ControlActions
import io.getstream.video.android.compose.ui.components.call.controls.actions.FlipCameraAction
import io.getstream.video.android.compose.ui.components.call.controls.actions.ToggleCameraAction
import io.getstream.video.android.compose.ui.components.call.controls.actions.ToggleMicrophoneAction
import io.getstream.video.android.core.GEO
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiR2VuZXJhbF9Eb2Rvbm5hIiwiaXNzIjoiaHR0cHM6Ly9wcm9udG8uZ2V0c3RyZWFtLmlvIiwic3ViIjoidXNlci9HZW5lcmFsX0RvZG9ubmEiLCJpYXQiOjE3MDI4MDE0NDAsImV4cCI6MTcwMzQwNjI0NX0.PXmp99tq1QEpxJr0s0JjJpBPD4coIsT9UpjCMGMMNiQ"
        val userId = "General_Crix_Madine"
        val callId = "DHYI5iYzvpTJ"

        // step1 - create a user.
        val user = User(
            id = userId, // any string
            name = "Nayem" // name and image are used in the UI
        )

        // step2 - initialize StreamVideo. For a production app we recommend adding the client to your Application class or di module.
        val client = StreamVideoBuilder(
            context = applicationContext,
            apiKey = "mmhfdzb5evj2", // demo API key
            geo = GEO.GlobalEdgeNetwork,
            user = user,
            token = userToken,
        ).build()

        // step3 - join a call, which type is `default` and id is `123`.
        val call = client.call("default", callId)
        lifecycleScope.launch {
            val result = call.join(create = true)
            result.onError {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
        }

        setContent {
            LaunchCallPermissions(call = call)

            VideoTheme {
                val isCameraEnabled by call.camera.isEnabled.collectAsState()
                val isMicEnabled by call.microphone.isEnabled.collectAsState()
                val remoteParticipants by call.state.remoteParticipants.collectAsState()
                val remoteParticipant = remoteParticipants.firstOrNull()
                val me by call.state.me.collectAsState()
                val connection by call.state.connection.collectAsState()
                var parentSize: IntSize by remember { mutableStateOf(IntSize(0, 0)) }

                CallContent(
                    modifier = Modifier.background(color = VideoTheme.colors.appBackground),
                    call = call,
                    onBackPressed = {onBackPressed()},
                    controlsContent = {
                        ControlActions(
                            call = call,
                            actions = listOf(
                                {
                                    ToggleCameraAction(
                                        modifier = Modifier.size(52.dp),
                                        isCameraEnabled = isCameraEnabled,
                                        onCallAction = {call.camera.setEnabled(it.isEnabled)}
                                    )
                                },
                                {
                                    ToggleMicrophoneAction(
                                        modifier = Modifier.size(52.dp),
                                        isMicrophoneEnabled = isMicEnabled,
                                        onCallAction = { call.microphone.setEnabled(it.isEnabled) })
                                },
                                {
                                    FlipCameraAction(
                                        modifier = Modifier.size(52.dp),
                                        onCallAction = {call.camera.flip()})
                                }
                            )
                        )
                    }

                )
            }
        }
    }
}
