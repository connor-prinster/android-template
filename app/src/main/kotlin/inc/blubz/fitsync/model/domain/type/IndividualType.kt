package inc.blubz.fitsync.model.domain.type

import androidx.annotation.StringRes
import org.blubz.fitsync.R

enum class IndividualType(@StringRes val textResId: Int) {
    UNKNOWN(R.string.unkown),
    HEAD(R.string.head),
    SPOUSE(R.string.spouse),
    CHILD(R.string.child)
}