package com.cfx.customview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

/**
 * compose 原理
 */
class CustomComposeTheoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /**
         * view 层级
         * decorView：FrameLayout
         *     LinearLayout
         *          App Bar，setContentView（页面主布局）- android.R.id.content
         */
        /**
         * public fun ComponentActivity.setContent(
         *     parent: CompositionContext? = null,
         *     content: @Composable () -> Unit
         * ) {
         *     如果获取到，就转成 ComposeView，获取不到 existingComposeView 就为空
         *     val existingComposeView = window.decorView
         *         .findViewById<ViewGroup>(android.R.id.content)
         *         .getChildAt(0) as? ComposeView
         *
         *     if (existingComposeView != null) with(existingComposeView) {
         *         setParentCompositionContext(parent) // ComposeView 的函数
         *         setContent(content) // ComposeView 的函数
         *     } else ComposeView(this).apply { // existingComposeView 为空就重新创建一个，不为空就直接调用上述代码
         *         // Set content and parent **before** setContentView
         *         // to have ComposeView create the composition on attach
         *         setParentCompositionContext(parent)
         *         setContent(content)
         *         // Set the view tree owners before setting the content view so that the inflation process
         *         // and attach listeners will see them already present
         *         setOwners()
         *         setContentView(this, DefaultActivityContentLayoutParams)
         *     }
         * }
         * 所以，主要工作还是在 ComposeView 的 setParentCompositionContext(parent)、setContent(content)
         *
         * fun setParentCompositionContext(parent: CompositionContext?) {
         *     parentContext = parent
         * }
         *
         * fun setContent(content: @Composable () -> Unit) {
         *     shouldCreateCompositionOnAttachedToWindow = true
         *     this.content.value = content // 将 setContent 中的 lambda 表达式函数传过来保存
         *     if (isAttachedToWindow) { // onCreate 中还没有 attach，所以不会执行
         *         createComposition()
         *     }
         * }
         *
         * 在 onAttachedToWindow 时会执行 ensureCompositionCreated()
         * override fun onAttachedToWindow() {
         *     super.onAttachedToWindow()
         *
         *     previousAttachedWindowToken = windowToken
         *
         *     if (shouldCreateCompositionOnAttachedToWindow) {
         *         ensureCompositionCreated()
         *     }
         * }
         *
         * private fun ensureCompositionCreated() {
         *     if (composition == null) {
         *         try {
         *             creatingComposition = true
         *             // resolveParentCompositionContext() 用于获取一个用于刷新界面的 Recomposer
         *             composition = setContent(resolveParentCompositionContext()) {
         *                 Content()
         *             }
         *         } finally {
         *             creatingComposition = false
         *         }
         *     }
         * }
         *
         * setContent(resolveParentCompositionContext()) 调到了 AbstractComposeView.setContent
         *
         * internal fun AbstractComposeView.setContent(
         *     parent: CompositionContext,
         *     content: @Composable () -> Unit
         * ): Composition {
         *     GlobalSnapshotManager.ensureStarted() // 有新值时，触发对应区域重组
         *     // 创建或者拿到 AndroidComposeView
         *     val composeView =
         *         if (childCount > 0) {
         *             getChildAt(0) as? AndroidComposeView
         *         } else {
         *             removeAllViews(); null
         *         } ?: AndroidComposeView(context, parent.effectCoroutineContext).also {
         *             addView(it.view, DefaultLayoutParams)
         *         }
         *     return doSetContent(composeView, parent, content)
         * }
         *
         *@Composable
         *   override fun Content() {
         *       content.value?.invoke()
         *   }
         *
         *fun ensureStarted() {
         *      if (started.compareAndSet(false, true)) {
         *          val channel = Channel<Unit>(Channel.CONFLATED)
         *          CoroutineScope(AndroidUiDispatcher.Main).launch {
         *              channel.consumeEach {
         *                  // 触发重组
         *                  Snapshot.sendApplyNotifications()
         *              }
         *          }
         *          Snapshot.registerGlobalWriteObserver {
         *              // 注册观察者
         *              channel.trySend(Unit)
         *          }
         *      }
         *  }
         *
         * compose 中层级
         * ComposeView
         *      AndroidComposeView -- 真正工作的 view
         *          LayoutNode
         *
         * private fun doSetContent(
         *     owner: AndroidComposeView,
         *     parent: CompositionContext,
         *     content: @Composable () -> Unit
         * ): Composition {
         *     if (inspectionWanted(owner)) {
         *         owner.setTag(
         *             R.id.inspection_slot_table_set,
         *             Collections.newSetFromMap(WeakHashMap<CompositionData, Boolean>())
         *         )
         *         enableDebugInspectorInfo()
         *     }
         *     val original = Composition(UiApplier(owner.root), parent)
         *     val wrapped = owner.view.getTag(R.id.wrapped_composition_tag)
         *         as? WrappedComposition
         *         ?: WrappedComposition(owner, original).also {
         *             owner.view.setTag(R.id.wrapped_composition_tag, it)
         *         }
         *     wrapped.setContent(content)
         *     return wrapped
         * }
         */
        setContent {
           Text("荒天帝", fontSize = 26.sp, modifier = Modifier.background(Color.Red))
        }
    }
}