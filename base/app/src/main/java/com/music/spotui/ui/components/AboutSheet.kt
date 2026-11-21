package com.music.spotui.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.myspoti.engine.BuildConfig
import app.myspoti.engine.R
import com.music.spotui.data.api.SupportReminder

const val LINKS_URL = "https://guns.lol/conjex"
const val GITHUB_PROFILE_URL = "https://github.com/conjex"

private fun openUrl(context: Context, url: String) {
    runCatching {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}

/** About sheet: version, personal links, AI disclosure, and GitHub button. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutSheet(onDismiss: () -> Unit) {
    val context = LocalContext.current
    // Opened in full: half open, it hid the support buttons at the bottom.
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color(0xFF282828),
    ) {
        Column(Modifier.navigationBarsPadding().verticalScroll(rememberScrollState()).padding(bottom = 20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 20.dp)) {
                Image(
                    painterResource(R.drawable.ic_launcher_foreground), contentDescription = null,
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(14.dp)).background(Color(0xFF121212)),
                )
                Column(Modifier.padding(start = 14.dp)) {
                    Text("sigma spotify", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("${stringResource(R.string.settings_item_version_title)} ${BuildConfig.VERSION_NAME}",
                        color = Color(0xFFB3B3B3), fontSize = 13.sp)
                }
            }
            Text(stringResource(R.string.about_free), color = Color(0xFFB3B3B3), fontSize = 13.sp,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 8.dp))
            AboutRow("Links", "guns.lol/conjex") { openUrl(context, LINKS_URL) }
            AboutRow("conjexed", "github.com/conjex") { openUrl(context, GITHUB_PROFILE_URL) }
            Text("Made with help of AI", color = Color(0xFFB3B3B3), fontSize = 13.sp,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 12.dp))
            GitHubSponsorButton(Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp), label = "Open on GitHub") { openUrl(context, GITHUB_PROFILE_URL) }
        }
    }
}

/** Outlined GitHub button: sponsor by default, or a caller-supplied profile action. */
@Composable
fun GitHubSponsorButton(modifier: Modifier = Modifier, label: String = "Sponsor on GitHub", onClick: (() -> Unit)? = null) {
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth().height(48.dp).clip(RoundedCornerShape(50))
            .border(1.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(50))
            .pressable(0.96f) { onClick?.invoke() ?: openUrl(context, SupportReminder.SPONSORS_URL) },
    ) { Text(label, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
}

/** Support sheet opened from the profile menu. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportSheet(onDismiss: () -> Unit) {
    val context = LocalContext.current
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color(0xFF282828),
    ) {
        androidx.compose.foundation.layout.Column(Modifier.padding(start = 20.dp, end = 20.dp, bottom = 28.dp)) {
            Text(stringResource(R.string.support_title), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Made with help of AI", color = Color(0xFFB3B3B3), fontSize = 14.sp,
                modifier = Modifier.padding(top = 10.dp, bottom = 18.dp))
            GitHubSponsorButton(Modifier.padding(top = 10.dp), label = "Open on GitHub") { onDismiss(); openUrl(context, GITHUB_PROFILE_URL) }
        }
    }
}

@Composable
private fun AboutRow(title: String, detail: String, onClick: () -> Unit) {
    Column(Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 20.dp, vertical = 10.dp)) {
        Text(title, color = Color.White, fontSize = 16.sp)
        Text(detail, color = Color(0xFFB3B3B3), fontSize = 12.sp)
    }
}
