package com.example.bottomsheetbug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class PackageItem(
    val id: Int,
    val name: String,
    val price: String,
)

private val samplePackages = listOf(
    "7 days" to listOf(
        PackageItem(1, "1 GB", "$5.00 USD"),
    ),
    "30 days" to listOf(
        PackageItem(2, "3 GB", "$9.00 USD"),
        PackageItem(3, "5 GB", "$13.00 USD"),
        PackageItem(4, "10 GB", "$25.00 USD"),
        PackageItem(5, "20 GB", "$37.00 USD"),
        PackageItem(6, "50 GB", "$69.00 USD"),
    ),
    "90 days" to listOf(
        PackageItem(7, "5 GB", "$15.00 USD"),
        PackageItem(8, "10 GB", "$29.00 USD"),
        PackageItem(9, "30 GB", "$55.00 USD"),
        PackageItem(10, "50 GB", "$79.00 USD"),
        PackageItem(11, "100 GB", "$129.00 USD"),
    ),
)

/**
 * Mimics a real-world bottom sheet layout:
 * - Scaffold with fillMaxHeight(0.90f) — the sheet content takes 90% of screen
 * - Custom top bar with title + close button (no drag handle)
 * - LazyColumn with grouped items
 * - Fixed bottom bar with action button
 *
 * The LazyColumn's nested scroll events leak to the ModalBottomSheet's
 * AnchoredDraggable, causing jitter when scrolling rapidly.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleBottomSheetContent(
    onDismiss: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxHeight(fraction = 0.90f),
        topBar = { SheetTopBar(onDismiss = onDismiss) },
        bottomBar = { SheetBottomBar() },
    ) { padding ->
        PackageList(scaffoldPadding = padding)
    }
}

@Composable
private fun SheetTopBar(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .height(5.dp)
                .width(40.dp)
                .align(Alignment.CenterHorizontally)
                .background(
                    color = Color.Gray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp),
                ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Available Packages",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                )
            }
        }
    }
}

@Composable
private fun SheetBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(16.dp),
    ) {
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Buy now")
        }
    }
}

@Composable
private fun PackageList(scaffoldPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = scaffoldPadding.calculateTopPadding())
            .background(color = MaterialTheme.colorScheme.surfaceContainerLowest),
    ) {
        samplePackages.forEachIndexed { groupIndex, (duration, packages) ->
            if (groupIndex > 0) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            item {
                Text(
                    text = duration,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
                )
            }

            items(packages) { pkg ->
                PackageCard(pkg)
            }

            if (groupIndex == samplePackages.lastIndex) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Spacer(modifier = Modifier.height(scaffoldPadding.calculateBottomPadding()))
                }
            }
        }
    }
}

@Composable
private fun PackageCard(pkg: PackageItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = pkg.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = pkg.price,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
