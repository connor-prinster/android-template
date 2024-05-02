package inc.blubz.fitsync.ui.compose.contentGrid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import inc.blubz.fitsync.ui.compose.PreviewDefault
import inc.blubz.fitsync.ui.theme.AppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentGrid(
    modifier: Modifier = Modifier,
    columnCount: Int = 2,
    contentList: List<ContentGridItem>,
) {
    if (contentList.isEmpty()) return

    val mutableContentList = contentList.toMutableList()
    // make certain the FlowRow doesn't have one of the rows be unevenly large/small
    while (mutableContentList.size % columnCount != 0) {
        mutableContentList.add(
            ContentGridItem(title = "")
        )
    }
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        maxItemsInEachRow = columnCount
    ) {
        mutableContentList.forEach { gridItem ->
            ContentGridItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                item = gridItem,
            )
        }
    }

}

@Composable
private fun ContentGridItem(modifier: Modifier = Modifier, item: ContentGridItem) {
    val onClick = item.onClick
    Column(
        modifier = if (onClick == null) {
            modifier
        } else {
            modifier.clickable { onClick.invoke() }
        }
    ) {
        ContentGridItem(item = item)
    }
}

@Composable
private fun ContentGridItem(item: ContentGridItem) {
    if (item.isEmpty()) return
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = item.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@PreviewDefault
@Composable
private fun ContentListItemPreviewOdd() {
    AppTheme {
        ContentGrid(
            contentList = listOf(
                ContentGridItem(title = "Tatooine"),
                ContentGridItem(title = "Hoth"),
                ContentGridItem(title = "Yavin"),
            )
        )
    }
}

@PreviewDefault
@Composable
private fun ContentListItemPreviewEven() {
    AppTheme {
        ContentGrid(
            contentList = listOf(
                ContentGridItem(title = "Tatooine"),
                ContentGridItem(title = "Hoth"),
                ContentGridItem(title = "Yavin"),
                ContentGridItem(title = "Jakku"),
            )
        )
    }
}

@PreviewDefault
@Composable
private fun ContentListItemPreviewThree() {
    AppTheme {
        ContentGrid(
            contentList = listOf(
                ContentGridItem(title = "Tatooine"),
                ContentGridItem(title = "Hoth"),
                ContentGridItem(title = "Yavin"),
                ContentGridItem(title = "Jakku"),
            ),
            columnCount = 3
        )
    }
}