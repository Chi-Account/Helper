package chi.library.view.recyclerview.snaphelper

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class GallerySnapHelper : SnapHelper() {

    companion object {
        const val INVALID_DISTANCE = 0F
    }

    private var horizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val result = IntArray(2)
        result[0] = if (layoutManager.canScrollHorizontally()) {
            distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else {
            0
        }
        return result
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }

        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val snapViewPosition = layoutManager.getPosition(snapView)
        if (snapViewPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        val vectorForEnd = layoutManager.computeScrollVectorForPosition(itemCount - 1)
            ?: return RecyclerView.NO_POSITION

        var deltaJump: Int
        if (layoutManager.canScrollHorizontally()) {
            deltaJump = estimateNextPositionDiffForFling(
                layoutManager,
                getHorizontalHelper(layoutManager),
                velocityX,
                velocityY = 0
            )
            if (vectorForEnd.x < 0) {
                deltaJump = -deltaJump
            }
            val maxDeltaJump =
                layoutManager.width / layoutManager.getDecoratedMeasuredWidth(snapView)
            if (deltaJump > maxDeltaJump) {
                deltaJump = maxDeltaJump
            } else if (deltaJump < -maxDeltaJump) {
                deltaJump = -maxDeltaJump
            }
        } else {
            deltaJump = 0
        }

        if (deltaJump == 0) {
            return RecyclerView.NO_POSITION
        }

        var targetSnapPosition = snapViewPosition + deltaJump
        targetSnapPosition = max(targetSnapPosition, 0)
        targetSnapPosition = min(targetSnapPosition, itemCount - 1)

        return targetSnapPosition
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (layoutManager is LinearLayoutManager) {
            val firstVisibleViewPosition = layoutManager.findFirstVisibleItemPosition()
            if (firstVisibleViewPosition == RecyclerView.NO_POSITION) {
                return null
            }

            if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
                return null
            }

            val horizontalHelper = getHorizontalHelper(layoutManager)

            val firstVisibleView = layoutManager.findViewByPosition(firstVisibleViewPosition)
            val firstVisibleViewEnd = horizontalHelper.getDecoratedEnd(firstVisibleView)
            val firstVisibleViewWidth = horizontalHelper.getDecoratedMeasurement(firstVisibleView)

            return if (firstVisibleViewEnd > 0 && firstVisibleViewEnd >= firstVisibleViewWidth / 2) {
                firstVisibleView
            } else {
                layoutManager.findViewByPosition(firstVisibleViewPosition + 1)
            }
        } else {
            return null
        }
    }

    private fun distanceToStart(view: View, orientationHelper: OrientationHelper): Int {
        return orientationHelper.getDecoratedStart(view) - orientationHelper.startAfterPadding
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        horizontalHelper.let {
            if (it == null || it.layoutManager != layoutManager) {
                horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
            }
        }
        return horizontalHelper!!
    }

    private fun estimateNextPositionDiffForFling(
        layoutManager: RecyclerView.LayoutManager,
        orientationHelper: OrientationHelper,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val scrollDistance = calculateScrollDistance(velocityX, velocityY)
        val distancePerChild = calculateDistancePerChild(layoutManager, orientationHelper)
        if (distancePerChild <= INVALID_DISTANCE) {
            return 0
        }
        val distance = scrollDistance[0]
        return if (distance > 0) {
            floor(distance / distancePerChild).toInt()
        } else {
            ceil(distance / distancePerChild).toInt()
        }
    }

    private fun calculateDistancePerChild(
        layoutManager: RecyclerView.LayoutManager,
        orientationHelper: OrientationHelper
    ): Float {
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return INVALID_DISTANCE
        }
        var minPositionView: View? = null
        var maxPositionView: View? = null
        var minPosition = Integer.MAX_VALUE
        var maxPosition = Integer.MIN_VALUE

        for (i in 0 until childCount) {
            val childView = layoutManager.getChildAt(i) ?: continue
            val childViewPosition = layoutManager.getPosition(childView)
            if (childViewPosition == RecyclerView.NO_POSITION) {
                continue
            }
            if (childViewPosition < minPosition) {
                minPosition = childViewPosition
                minPositionView = childView
            }
            if (childViewPosition > maxPosition) {
                maxPosition = childViewPosition
                maxPositionView = childView
            }
        }

        if (minPositionView == null || maxPositionView == null) {
            return INVALID_DISTANCE
        }
        val start = min(
            orientationHelper.getDecoratedStart(minPositionView),
            orientationHelper.getDecoratedStart(maxPositionView)
        )
        val end = max(
            orientationHelper.getDecoratedEnd(minPositionView),
            orientationHelper.getDecoratedEnd(maxPositionView)
        )
        val distance = end - start
        if (distance == 0) {
            return INVALID_DISTANCE
        }
        return distance.toFloat() / (maxPosition - minPosition + 1)
    }
}