package inc.blubz.fitsync.ui.compose.contentGrid

class ContentGridItem(val title: String, val onClick: (() -> Unit)? = null) {
    fun isEmpty(): Boolean {
        return title.isEmpty()
    }
}