package com.ronin.horizontal_wheel_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp

/**
 * A customizable wheel picker component for Android Jetpack Compose.
 *
 * The `HorizontalWheelPicker` allows users to select an item using a list of vertical lines displayed horizontally
 * as vertical lines. The selected item is highlighted, and surrounding items can be
 * customized with different heights, colors, and transparency effects. The component
 * supports dynamic width, customizable item spacing, and rounded corners.
 *
 * @param modifier The modifier to be applied to the `WheelPicker` component.
 * @param wheelPickerWidth The width of the entire picker. If null, the picker will use the full screen width. Default is `null`.
 * @param totalItems The total number of items in the picker.
 * @param initialSelectedItem The index of the item that is initially selected.
 * @param lineWidth The width of each vertical line in the picker. Default is `2.sdp`.
 * @param selectedLineHeight The height of the selected item (line) in the picker. Default is `64.sdp`.
 * @param multipleOfFiveLineHeight The height of lines at indices that are multiples of 5. Default is `40.sdp`.
 * @param normalLineHeight The height of lines that are not the selected item or multiples of 5. Default is `30.sdp`.
 * @param selectedMultipleOfFiveLinePaddingBottom The padding bottom for the selected item (line) that are multiples of 5. Default is `0.sdp`.
 * @param normalMultipleOfFiveLinePaddingBottom The padding bottom for lines that are multiples of 5. Default is `6.sdp`.
 * @param normalLinePaddingBottom The padding bottom for normal lines. Default is `8.sdp`.
 * @param lineSpacing The spacing between each line in the picker. Default is `8.sdp`.
 * @param lineRoundedCorners The corner radius of each vertical line, applied to create rounded corners. Default is `2.sdp`.
 * @param selectedLineColor The color of the selected item (line) in the picker. Default is `Color(0xFF00D1FF)`.
 * @param unselectedLineColor The color of the unselected items (lines) in the picker. Default is `Color.LightGray`.
 * @param fadeOutLinesCount The number of lines at the edges of the picker that will gradually fade out. Default is `4`.
 * @param maxFadeTransparency The maximum transparency level applied to the fading lines. Default is `0.7f`.
 * @param onItemSelected A callback function invoked when a new item is selected, passing the selected index as a parameter.
 *
 */
@Composable
fun HorizontalWheelPicker(
    modifier: Modifier = Modifier,
    wheelPickerWidth: Dp? = null,
    totalItems: Int,
    initialSelectedItem: Int,
    lineWidth: Dp = 2.sdp,
    selectedLineHeight: Dp = 64.sdp,
    multipleOfFiveLineHeight: Dp = 40.sdp,
    normalLineHeight: Dp = 30.sdp,
    selectedMultipleOfFiveLinePaddingBottom: Dp = 0.sdp,
    normalMultipleOfFiveLinePaddingBottom: Dp = 6.sdp,
    normalLinePaddingBottom: Dp = 8.sdp,
    lineSpacing: Dp = 8.sdp,
    lineRoundedCorners: Dp = 2.sdp,
    selectedLineColor: Color = Color(0xFF00D1FF),
    unselectedLineColor: Color = Color.LightGray,
    fadeOutLinesCount: Int = 4,
    maxFadeTransparency: Float = 0.7f,
    onItemSelected: (Int) -> Unit
) {
    val screenWidthDp = LocalContext.current.resources.displayMetrics.run {
        widthPixels / density
    }.dp
    val effectiveWidth = wheelPickerWidth ?: screenWidthDp

    var currentSelectedItem by remember { mutableIntStateOf(initialSelectedItem) }
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = initialSelectedItem)

    val visibleItemsInfo by remember { derivedStateOf { scrollState.layoutInfo.visibleItemsInfo } }
    val firstVisibleItemIndex = visibleItemsInfo.firstOrNull()?.index ?: -1
    val lastVisibleItemIndex = visibleItemsInfo.lastOrNull()?.index ?: -1
    val totalVisibleItems = lastVisibleItemIndex - firstVisibleItemIndex + 1
    val middleIndex = firstVisibleItemIndex + totalVisibleItems / 2
    val bufferIndices = totalVisibleItems / 2

    LaunchedEffect(currentSelectedItem) {
        onItemSelected(currentSelectedItem)
    }

    LazyRow(
        modifier = modifier.width(effectiveWidth),
        state = scrollState,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(totalItems + totalVisibleItems) { index ->
            val adjustedIndex = index - bufferIndices

            if (index == middleIndex) {
                currentSelectedItem = adjustedIndex
            }

            val lineHeight = when {
                index == middleIndex -> selectedLineHeight
                adjustedIndex % 5 == 0 -> multipleOfFiveLineHeight
                else -> normalLineHeight
            }

            val paddingBottom = when {
                index == middleIndex -> selectedMultipleOfFiveLinePaddingBottom
                adjustedIndex % 5 == 0 -> normalMultipleOfFiveLinePaddingBottom
                else -> normalLinePaddingBottom
            }

            val lineTransparency = calculateLineTransparency(
                index,
                totalItems,
                bufferIndices,
                firstVisibleItemIndex,
                lastVisibleItemIndex,
                fadeOutLinesCount,
                maxFadeTransparency
            )

            VerticalLine(
                lineWidth = lineWidth,
                lineHeight = lineHeight,
                paddingBottom = paddingBottom,
                roundedCorners = lineRoundedCorners,
                indexAtCenter = index == middleIndex,
                lineTransparency = lineTransparency,
                selectedLineColor = selectedLineColor,
                unselectedLineColor = unselectedLineColor
            )

            Spacer(modifier = Modifier.width(lineSpacing))
        }
    }
}

/**
 * A composable function that renders a single vertical line in the `WheelPicker`.
 *
 * The `VerticalLine` component is used within the `WheelPicker` to represent each
 * selectable item as a vertical line. The line's appearance can be customized with
 * different heights, padding, rounded corners, colors, and transparency effects.
 *
 * @param lineWidth The width of the vertical line.
 * @param lineHeight The height of the vertical line.
 * @param paddingBottom The padding applied to the bottom of the line.
 * @param roundedCorners The corner radius applied to the line, creating rounded corners.
 * @param indexAtCenter A boolean flag indicating if the line is at the center (selected item).
 * @param lineTransparency The transparency level applied to the line.
 * @param selectedLineColor The color of the line if it is the selected item.
 * @param unselectedLineColor The color of the line if it is not the selected item.
 *
 */
@Composable
private fun VerticalLine(
    lineWidth: Dp,
    lineHeight: Dp,
    paddingBottom: Dp,
    roundedCorners: Dp,
    indexAtCenter: Boolean,
    lineTransparency: Float,
    selectedLineColor: Color,
    unselectedLineColor: Color
) {
    Box(
        modifier = Modifier
            .width(lineWidth)
            .height(lineHeight)
            .clip(RoundedCornerShape(roundedCorners))
            .alpha(lineTransparency)
            .background(if (indexAtCenter) selectedLineColor else unselectedLineColor)
            .padding(bottom = paddingBottom)
    )
}

/**
 * Calculates the transparency level for a line based on its position within the `WheelPicker`.
 *
 * This function determines the transparency level for a line in the `WheelPicker` based on
 * its index and its position relative to the visible items in the list. The transparency
 * gradually increases towards the edges of the picker, creating a fade-out effect.
 *
 * @param lineIndex The index of the current line being rendered.
 * @param totalLines The total number of lines in the picker.
 * @param bufferIndices The number of extra indices used for rendering outside the visible area.
 * @param firstVisibleItemIndex The index of the first visible item in the list.
 * @param lastVisibleItemIndex The index of the last visible item in the list.
 * @param fadeOutLinesCount The number of lines that should gradually fade out at the edges.
 * @param maxFadeTransparency The maximum transparency level to apply during the fade-out effect.
 * @return A `Float` value representing the calculated transparency level for the line.
 *
 */
private fun calculateLineTransparency(
    lineIndex: Int,
    totalLines: Int,
    bufferIndices: Int,
    firstVisibleItemIndex: Int,
    lastVisibleItemIndex: Int,
    fadeOutLinesCount: Int,
    maxFadeTransparency: Float
): Float {
    val actualCount = fadeOutLinesCount + 1
    val transparencyStep = maxFadeTransparency / actualCount

    return when {
        lineIndex < bufferIndices || lineIndex > (totalLines + bufferIndices) -> 0.0f
        lineIndex in firstVisibleItemIndex until firstVisibleItemIndex + fadeOutLinesCount -> {
            transparencyStep * (lineIndex - firstVisibleItemIndex + 1)
        }

        lineIndex in (lastVisibleItemIndex - fadeOutLinesCount + 1)..lastVisibleItemIndex -> {
            transparencyStep * (lastVisibleItemIndex - lineIndex + 1)
        }

        else -> 1.0f
    }
}