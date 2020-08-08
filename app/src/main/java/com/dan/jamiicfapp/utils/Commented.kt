package com.dan.jamiicfapp.utils

/*

<ScrollView
            android:id="@+id/layout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <androidx.constraintlayout.widget.ConstraintLayout
                android:id="@+id/layout2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="8dp"
                app:layout_constraintBottom_toBottomOf="parent">

                <Button
                    android:id="@+id/buttonAddItem"
                    android:layout_width="150dp"
                    android:layout_height="wrap_content"
                    android:layout_marginEnd="8dp"
                    android:text="Add Item"
                    app:layout_constraintEnd_toStartOf="@+id/buttonRemoveItem"
                    app:layout_constraintStart_toStartOf="@+id/editText"
                    app:layout_constraintTop_toBottomOf="@+id/editText" />

                <EditText
                    android:id="@+id/editText"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="32dp"
                    android:layout_marginTop="8dp"
                    android:layout_marginEnd="32dp"
                    android:ems="10"
                    android:text="1"
                    android:hint="Enter Position"
                    android:inputType="number"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />

                <Button
                    android:id="@+id/buttonRemoveItem"
                    android:layout_width="150dp"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="8dp"
                    android:text="RemoveItem"
                    app:layout_constraintEnd_toEndOf="@+id/editText"
                    app:layout_constraintStart_toEndOf="@+id/buttonAddItem"
                    app:layout_constraintTop_toBottomOf="@+id/editText" />

            </androidx.constraintlayout.widget.ConstraintLayout>


        </ScrollView>


buttonAddItem.setOnClickListener {
            if (!editText.text.toString().isEmpty()) {
                addItemAT(editText.text.toString().toInt())
            }
        }
        buttonRemoveItem.setOnClickListener {
            if (!editText.text.toString().isEmpty()) {
                removeItemAT(editText.text.toString().toInt())
            }

        }




    fun addItemAT(pos: Int) {
        detailsPWDs.add(
            DetailOfPWD(
                "TIGERS 4 LIONS BALLPARK",
                "We are encouraging Wewoka Alumni (Tigers); current & former residents; athletes; Little League supporters; & former ballplayers & coaches to lend financial support to this historic...",
                R.drawable.disability,
                " ${android.R.color.holo_red_dark}$30,875",
                "$150,000 raised"
            )
        )
        userAdapter.notifyItemInserted(pos)
        linearLayoutManager.scrollToPositionWithOffset(pos, 0)
    }

    fun removeItemAT(pos: Int) {
        detailsPWDs.removeAt(pos)
        userAdapter.notifyItemRemoved(pos)

    }



 val name = getColoredSpanned("30,875", "#D40012")
    val surName = getColoredSpanned("150,000", "#000")
    val amountdonated = Html.fromHtml("$name",FROM_HTML_MODE_LEGACY).toString()
    val amountrquirued = Html.fromHtml("$surName",FROM_HTML_MODE_LEGACY).toString()

    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }

*/