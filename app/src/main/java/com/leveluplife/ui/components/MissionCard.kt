package com.leveluplife.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.leveluplife.data.model.Mission
import com.leveluplife.ui.theme.CardSurfaceAlt
import com.leveluplife.ui.theme.TextMuted
import com.leveluplife.ui.theme.TextPrimary

@Composable
fun MissionCard(mission: Mission, onClick: (() -> Unit)? = null) {
    val color = difficultyColor(mission.difficulty)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .background(CardSurfaceAlt, RoundedCornerShape(16.dp))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .alpha(if (mission.isCompleted) 0.75f else 1f)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(categoryIcon(mission.category), color = TextPrimary)
            Text(mission.title, color = TextPrimary)
        }
        Text(mission.description, color = TextMuted)
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(mission.frequency.name, color = color)
            Text("+${mission.xpReward} XP", color = color)
        }
    }
}
