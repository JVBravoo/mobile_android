### Activity 01

- Create an Android project from scratch, without relying on any branches presented in the classroom
- In the main activity, present a list of Fruits, with photo, name, a small text with the benefits of fruit
- The list must be created using RecyclerView.
- In the main Activity there must be a FloatingButton, with a "+" symbol. Clicking this button opens a SecondActivity for result, which contains: An EditText for the Fruit name, An EditText(Multiline) for the benefits, and an Add Image button, which must send an implicit intent to acquire the gallery image.
- In SecondActivity, when clicking finish, it should send the Parcelable Fruit object back to the main Activity. That add a fruit to the list
- When clicking on the image of the fruit, you should open another activity, DetailsActivity.
- Which contains the details of the fruit (Name, photo, benefits) and contains the Remove option, which when clicked, removes the fruit from the list and returns to MainAcitivity
- If the cell phone is rotated, the listing must not be lost