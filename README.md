# HorizontalWheelPicker

`HorizontalWheelPicker` is a customizable wheel picker component for Jetpack Compose that allows
users to select an item from a horizontally scrolling list of vertical lines. It supports various
customization options for appearance, spacing, and behavior.

Check out this short GIF demonstrating the `HorizontalWheelPicker`:

![HorizontalWheelPicker Gif](/media/horizontal-wheel-picker.gif)

## Features

- **Customizable Item Appearance:** Adjust the height, color, and corner radius of each vertical
  line.
- **Dynamic Width:** Set a fixed width or use the full screen width.
- **Fade-Out Effect:** Lines at the edges of the picker gradually fade out.
- **Item Selection Callback:** Get notified when a new item is selected.

## Installation

Add the following to your `build.gradle` file to include the library in your project:

    dependencies {
        implementation ("io.github.shahalam-mz.horizontal-wheel-picker:horizontal-wheel-picker:TAG")
    }

## Usage

Here's how to use `HorizontalWheelPicker` in your Jetpack Compose project:

    @Composable
    fun MyWheelPicker() {
        HorizontalWheelPicker(
            modifier = Modifier.fillMaxWidth(),
            totalItems = 100,
            initialSelectedItem = 15,
            onItemSelected = { selectedIndex ->
                // Handle item selection
                println("Selected item index: $selectedIndex")
            }
        )
    }

## Parameters

The `HorizontalWheelPicker` composable function accepts the following parameters:

- **`modifier`** (`Modifier`):
    - *Description*: The modifier to be applied to the `HorizontalWheelPicker` component.
    - *Default*: `Modifier`

- **`wheelPickerWidth`** (`Dp?`):
    - *Description*: The width of the entire picker. If `null`, the picker will use the full screen width.
    - *Default*: `null`

- **`totalItems`** (`Int`):
    - *Description*: The total number of items in the picker.
    - *Default*: *Required parameter*

- **`initialSelectedItem`** (`Int`):
    - *Description*: The index of the item that is initially selected.
    - *Default*: *Required parameter*

- **`lineWidth`** (`Dp`):
    - *Description*: The width of each vertical line in the picker.
    - *Default*: `2.dp`

- **`selectedLineHeight`** (`Dp`):
    - *Description*: The height of the selected item (line) in the picker.
    - *Default*: `64.dp`

- **`multipleOfFiveLineHeight`** (`Dp`):
    - *Description*: The height of lines at indices that are multiples of 5.
    - *Default*: `40.dp`

- **`normalLineHeight`** (`Dp`):
    - *Description*: The height of lines that are not the selected item or multiples of 5.
    - *Default*: `30.dp`

- **`selectedMultipleOfFiveLinePaddingBottom`** (`Dp`):
    - *Description*: The padding bottom for the selected item (line) that are multiples of 5.
    - *Default*: `0.dp`

- **`normalMultipleOfFiveLinePaddingBottom`** (`Dp`):
    - *Description*: The padding bottom for lines that are multiples of 5.
    - *Default*: `6.dp`

- **`normalLinePaddingBottom`** (`Dp`):
    - *Description*: The padding bottom for normal lines.
    - *Default*: `8.dp`

- **`lineSpacing`** (`Dp`):
    - *Description*: The spacing between each line in the picker.
    - *Default*: `8.dp`

- **`lineRoundedCorners`** (`Dp`):
    - *Description*: The corner radius of each vertical line, applied to create rounded corners.
    - *Default*: `2.dp`

- **`selectedLineColor`** (`Color`):
    - *Description*: The color of the selected item (line) in the picker.
    - *Default*: `Color(0xFF00D1FF)`

- **`unselectedLineColor`** (`Color`):
    - *Description*: The color of the unselected items (lines) in the picker.
    - *Default*: `Color.LightGray`

- **`fadeOutLinesCount`** (`Int`):
    - *Description*: The number of lines at the edges of the picker that will gradually fade out.
    - *Default*: `4`

- **`maxFadeTransparency`** (`Float`):
    - *Description*: The maximum transparency level applied to the fading lines.
    - *Default*: `0.7f`

- **`onItemSelected`** (`(Int) -> Unit`):
    - *Description*: A callback function invoked when a new item is selected, passing the selected index as a parameter.
    - *Default*: *Required parameter*

## Contributing

Contributions are welcome! Here’s how to get involved:

- **Report Issues**: [Open an issue](https://github.com/shahalam-mz/horizontal-wheel-picker/issues) for bugs or feature requests.
- **Submit Code**: Fork the repo, create a branch, make your changes, and [submit a pull request](https://github.com/shahalam-mz/horizontal-wheel-picker/pulls).
- **Review and Feedback**: Reviews and feedback are appreciated to help improve the library.

## License

`HorizontalWheelPicker` is licensed under the Apache License, Version 2.0.

- **You may**: Use, modify, and distribute the software.
- **You must**: Include the license in redistributions and provide attribution.
- **You may not**: Use it in a way that suggests endorsement by the authors.

See the full license at [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).