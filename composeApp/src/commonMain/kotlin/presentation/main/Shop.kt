package presentation.main

import GGSansRegular
import StackedSnackbarHost
import androidx.compose.foundation.BorderStroke
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.ProductItemUIModel
import domain.Models.ProductResourceListEnvelope
import domain.Models.Screens
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.Products.SearchBar
import presentation.Products.HomeProductItem
import presentation.Products.ProductContract
import presentation.Products.ProductDetailContent
import presentation.Products.ProductPresenter
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductResourceListEnvelopeViewModel
import presentation.viewmodels.ProductViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.getPopularProductViewHeight


class ShopTab(private val mainViewModel: MainViewModel,
              private val productViewModel: ProductViewModel,
              private val productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel) : Tab, KoinComponent {

    private val productPresenter: ProductPresenter by inject()
    private var productUIStateViewModel: UIStateViewModel? = null

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Products"
            val icon = painterResource("drawable/shop_icon.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon

                )
            }
        }

    @Composable
    override fun Content() {
        val userId = mainViewModel.userId.collectAsState()
        val vendorId = mainViewModel.vendorId.collectAsState()
        val onCartChanged = remember { mutableStateOf(false) }
        val searchQuery = remember { mutableStateOf("") }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (productUIStateViewModel == null) {
            productUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        if (productResourceListEnvelopeViewModel.resources.value.isNotEmpty()){
            productViewModel.setProductUIState(
                AsyncUIStates(
                    isDone = true,
                    isSuccess = true
                )
            )
        }
        else {
            if (userId.value != -1 && vendorId.value != -1) {
                productPresenter.getProducts(vendorId.value)
            }
        }

        val productHandler = ShopProductsHandler(
            productUIStateViewModel!!, productPresenter,
            onPageLoading = {
                productViewModel!!.setProductUIState(AsyncUIStates(isLoading = true))
            },
            onProductAvailable = { products: ProductResourceListEnvelope?, isFromSearch: Boolean ->
                productViewModel!!.setProductUIState(
                    AsyncUIStates(
                        isDone = true,
                        isSuccess = true
                    )
                )
                if (isFromSearch) {
                    // New Search Created
                    if (productResourceListEnvelopeViewModel.resources.value.isEmpty()) {
                        productResourceListEnvelopeViewModel!!.setResources(products?.resources)
                        products?.prevPageUrl?.let {
                            productResourceListEnvelopeViewModel!!.setPrevPageUrl(
                                it
                            )
                        }
                        products?.nextPageUrl?.let {
                            productResourceListEnvelopeViewModel!!.setNextPageUrl(
                                it
                            )
                        }
                        products?.currentPage?.let {
                            productResourceListEnvelopeViewModel!!.setCurrentPage(
                                it
                            )
                        }
                        products?.totalItemCount?.let {
                            productResourceListEnvelopeViewModel!!.setTotalItemCount(
                                it
                            )
                        }
                        products?.displayedItemCount?.let {
                            productResourceListEnvelopeViewModel!!.setDisplayedItemCount(
                                it
                            )
                        }
                    }

                    // Add more from search
                    else if (productResourceListEnvelopeViewModel!!.resources.value.isNotEmpty()) {
                        val productList = productResourceListEnvelopeViewModel!!.resources.value
                        productList.addAll(products?.resources!!)
                        productResourceListEnvelopeViewModel!!.setResources(productList)
                        products.prevPageUrl?.let {
                            productResourceListEnvelopeViewModel!!.setPrevPageUrl(
                                it
                            )
                        }
                        products.nextPageUrl?.let {
                            productResourceListEnvelopeViewModel!!.setNextPageUrl(
                                it
                            )
                        }
                        products.currentPage?.let {
                            productResourceListEnvelopeViewModel!!.setCurrentPage(
                                it
                            )
                        }
                        products.totalItemCount?.let {
                            productResourceListEnvelopeViewModel!!.setTotalItemCount(
                                it
                            )
                        }
                        products.displayedItemCount?.let {
                            productResourceListEnvelopeViewModel!!.setDisplayedItemCount(
                                it
                            )
                        }
                    }
                }
                else{

                    if (productResourceListEnvelopeViewModel.resources.value.isEmpty()) {
                        productResourceListEnvelopeViewModel!!.setResources(products?.resources)
                        products?.prevPageUrl?.let {
                            productResourceListEnvelopeViewModel!!.setPrevPageUrl(
                                it
                            )
                        }
                        products?.nextPageUrl?.let {
                            productResourceListEnvelopeViewModel!!.setNextPageUrl(
                                it
                            )
                        }
                        products?.currentPage?.let {
                            productResourceListEnvelopeViewModel!!.setCurrentPage(
                                it
                            )
                        }
                        products?.totalItemCount?.let {
                            productResourceListEnvelopeViewModel!!.setTotalItemCount(
                                it
                            )
                        }
                        products?.displayedItemCount?.let {
                            productResourceListEnvelopeViewModel!!.setDisplayedItemCount(
                                it
                            )
                        }
                    }

                    // Add more from search
                    else if (productResourceListEnvelopeViewModel!!.resources.value.isNotEmpty()) {
                        val productList = productResourceListEnvelopeViewModel!!.resources.value
                        productList.addAll(products?.resources!!)
                        productResourceListEnvelopeViewModel!!.setResources(productList)
                        products.prevPageUrl?.let {
                            productResourceListEnvelopeViewModel!!.setPrevPageUrl(
                                it
                            )
                        }
                        products.nextPageUrl?.let {
                            productResourceListEnvelopeViewModel!!.setNextPageUrl(
                                it
                            )
                        }
                        products.currentPage?.let {
                            productResourceListEnvelopeViewModel!!.setCurrentPage(
                                it
                            )
                        }
                        products.totalItemCount?.let {
                            productResourceListEnvelopeViewModel!!.setTotalItemCount(
                                it
                            )
                        }
                        products.displayedItemCount?.let {
                            productResourceListEnvelopeViewModel!!.setDisplayedItemCount(
                                it
                            )
                        }
                    }

                }

            }, onErrorVisible = {
                productViewModel!!.setProductUIState(
                    AsyncUIStates(
                        isSuccess = false,
                        isDone = true
                    )
                )
            }, onLoadMoreStarted = { isLoadMore ->
                productResourceListEnvelopeViewModel.setLoadingMore(true)
            }, onLoadMoreEnded = {
                productResourceListEnvelopeViewModel.setLoadingMore(false)
            })
        productHandler.init()

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                SearchBar(onValueChange = {
                    if (it.isNotEmpty()) {
                        if (!productResourceListEnvelopeViewModel!!.isSearching.value) {
                            if (it.isNotEmpty()) {
                                productResourceListEnvelopeViewModel.clearData(mutableListOf())
                                searchQuery.value = it
                                productPresenter.searchProducts(
                                    mainViewModel.vendorId.value,
                                    it
                                )
                            }
                        }
                    }
                }, onBackPressed = {
                    productResourceListEnvelopeViewModel.clearData(mutableListOf())
                    productPresenter.getProducts(vendorId.value)
                })
            },
            content = {
                ProductContent(
                    productResourceListEnvelopeViewModel = productResourceListEnvelopeViewModel,
                    productViewModel = productViewModel,
                    searchQuery = searchQuery.value,
                    vendorId = vendorId.value,
                    mainViewModel = mainViewModel,
                    onCartChanged = {
                        onCartChanged.value = true
                    })
            },
            backgroundColor = Color.White,
            floatingActionButton = {
                var cartSize = mainViewModel.unSavedOrders.value.size
                if (onCartChanged.value) {
                    cartSize = mainViewModel.unSavedOrders.value.size
                }
                val cartContainer = if (cartSize > 0) 140 else 0
                Box(
                    modifier = Modifier.size(cartContainer.dp)
                        .padding(bottom = 40.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    AttachShoppingCartImage("drawable/shopping_cart.png", mainViewModel)
                }
            }
        )
    }

    @Composable
    fun AttachShoppingCartImage(iconRes: String, mainViewModel: MainViewModel) {
        val currentOrders = mainViewModel.unSavedOrders.collectAsState()

        val indicatorModifier = Modifier
            .padding(end = 15.dp, bottom = 20.dp)
            .background(color = Color.Transparent)
            .size(30.dp)
            .clip(CircleShape)
            .background(color = Color(color = 0xFFFF5080))

        Box(
            Modifier
                .clip(CircleShape)
                .size(70.dp)
                .clickable {
                    mainViewModel.setScreenNav(
                        Pair(Screens.MAIN_TAB.toPath(), Screens.CART.toPath())
                    )
                }
                .background(color = Colors.primaryColor),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .size(40.dp)
            ImageComponent(
                imageModifier = modifier,
                imageRes = iconRes,
                colorFilter = ColorFilter.tint(color = Color.White)
            )
            Box(
                modifier = indicatorModifier,
                contentAlignment = Alignment.Center
            ) {
                TextComponent(
                    text = currentOrders.value.size.toString(),
                    fontSize = 17,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }


    @Composable
    fun ProductContent(
        productResourceListEnvelopeViewModel: ProductResourceListEnvelopeViewModel,
        searchQuery: String, vendorId: Int,
        productViewModel: ProductViewModel, mainViewModel: MainViewModel, onCartChanged: () -> Unit
    ) {
        val loadMoreState = productResourceListEnvelopeViewModel.isLoadingMore.collectAsState()
        val productList = productResourceListEnvelopeViewModel.resources.collectAsState()
        val selectedProduct = remember { mutableStateOf(Product()) }
        val productUIState = productViewModel.productUiState.collectAsState()
        val totalProductsCount = productResourceListEnvelopeViewModel.totalItemCount.collectAsState()
        val displayedProductsCount = productResourceListEnvelopeViewModel.displayedItemCount.collectAsState()
        val lastIndex = productList.value.size.minus(1)
        var productUIModel by remember {
            mutableStateOf(
                ProductItemUIModel(
                    selectedProduct.value,
                    productList.value
                )
            )
        }

        if (!loadMoreState.value) {
            productUIModel = productUIModel.copy(selectedProduct = selectedProduct.value,
                productList = productResourceListEnvelopeViewModel.resources.value.map { it2 ->
                    it2.copy(
                        isSelected = it2.productId == selectedProduct.value.vendorId
                    )
                })
        }

        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            if (productUIState.value.isLoading) {
                productResourceListEnvelopeViewModel.setIsSearching(true)
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    IndeterminateCircularProgressBar()
                }
            } else if (productUIState.value.isDone && !productUIState.value.isSuccess) {
                productResourceListEnvelopeViewModel.setIsSearching(false)
                // Error Occurred display reload
            } else if (productUIState.value.isDone && productUIState.value.isSuccess) {
                productResourceListEnvelopeViewModel.setIsSearching(false)
                val columnModifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = columnModifier
                ) {
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(color = Color.White),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        var showProductDetailBottomSheet by remember { mutableStateOf(false) }
                        if (showProductDetailBottomSheet) {
                            ProductDetailBottomSheet(
                                mainViewModel,
                                isViewedFromCart = false,
                                OrderItem(itemProduct = selectedProduct.value),
                                onDismiss = { isAddToCart, item ->
                                    onCartChanged()
                                    showProductDetailBottomSheet = false

                                },
                                onRemoveFromCart = {})
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(
                                getPopularProductViewHeight(productUIModel.productList).dp
                            ),
                            contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            userScrollEnabled = true
                        ) {
                            items(productUIModel.productList.size) { it ->
                                HomeProductItem(
                                    productUIModel.productList[it],
                                    onProductClickListener = { it2 ->
                                        selectedProduct.value = it2
                                        showProductDetailBottomSheet = true
                                    })

                                if (it == lastIndex && loadMoreState.value) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(60.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IndeterminateCircularProgressBar()
                                    }
                                }
                                else if (it == lastIndex && (displayedProductsCount.value < totalProductsCount.value)) {
                                    val buttonStyle = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)

                                    ButtonComponent(
                                        modifier = buttonStyle,
                                        buttonText = "Show More",
                                        borderStroke = BorderStroke(1.dp, theme.Colors.primaryColor),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                        fontSize = 16,
                                        shape = CircleShape,
                                        textColor = theme.Colors.primaryColor,
                                        style = TextStyle()
                                    ) {
                                        if (productResourceListEnvelopeViewModel.nextPageUrl.value.isNotEmpty()) {
                                            productPresenter.searchMoreProducts(vendorId = vendorId, searchQuery = searchQuery,
                                                nextPage = productResourceListEnvelopeViewModel.currentPage.value + 1)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    class ShopProductsHandler(
        private val uiStateViewModel: UIStateViewModel,
        private val productPresenter: ProductPresenter,
        private val onPageLoading: () -> Unit,
        private val onProductAvailable: (products: ProductResourceListEnvelope?, isFromSearch: Boolean) -> Unit,
        private val onLoadMoreStarted: (isLoadMore: Boolean) -> Unit,
        private val onLoadMoreEnded: (isLoadMore: Boolean) -> Unit,
        private val onErrorVisible: () -> Unit
    ) : ProductContract.View {
        fun init() {
            productPresenter.registerUIContract(this)
        }

        override fun showLce(uiState: UIStates, message: String) {
            uiStateViewModel.switchState(uiState)
            uiState.let {
                when {
                    it.loadingVisible -> {
                        onPageLoading()
                    }

                    it.errorOccurred -> {
                        onErrorVisible()
                    }
                }
            }
        }

        override fun showProducts(products: ProductResourceListEnvelope?, isFromSearch: Boolean) {
            onProductAvailable(products, isFromSearch)
        }

        override fun onLoadMoreProductStarted(isSuccess: Boolean) {
            onLoadMoreStarted(true)
        }

        override fun onLoadMoreProductEnded(isSuccess: Boolean) {
            onLoadMoreEnded(false)
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductDetailBottomSheet(mainViewModel: MainViewModel, isViewedFromCart: Boolean = false, selectedProduct: OrderItem, onDismiss: (isAddToCart: Boolean, OrderItem) -> Unit, onRemoveFromCart: (OrderItem) -> Unit) {
        val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            modifier = Modifier.padding(top = 20.dp),
            onDismissRequest = { onDismiss(false, selectedProduct) },
            sheetState = modalBottomSheetState,
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            containerColor = Color(0xFFF3F3F3),
            dragHandle = {},
        ) {
            ProductDetailContent(mainViewModel,isViewedFromCart,selectedProduct, onAddToCart = {
                onDismiss(it,selectedProduct)
            }, onRemoveFromCart = {
                onRemoveFromCart(it)
            })
        }
    }

}