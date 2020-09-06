// package com.ezike.tobenna.starwarssearch.presentation.mvi
//
// import kotlinx.coroutines.channels.ConflatedBroadcastChannel
// import kotlinx.coroutines.flow.Flow
// import kotlinx.coroutines.flow.asFlow
// import kotlinx.coroutines.flow.flatMapLatest
// import kotlinx.coroutines.flow.onEach
// import kotlinx.coroutines.flow.scan
// import java.util.concurrent.CopyOnWriteArrayList
//
// abstract class StoreImpl<I : ViewIntent, S : ScreenState, out R : ViewResult>(
//     private val intentProcessor: IntentProcessor<I, R>,
//     private val reducer: ViewStateReducer<S, R>,
//     initialState: S,
//     initialIntent: I? = null
// ) {
//
//     private val subscriptions: CopyOnWriteArrayList<Subscription> = CopyOnWriteArrayList()
//
//     private var oldState: S = initialState
//
//     private val intentsChannel: ConflatedBroadcastChannel<I> =
//         ConflatedBroadcastChannel<I>().apply {
//             if (initialIntent != null) {
//                 offer(initialIntent)
//             }
//         }
//
//     internal fun subscribe(subscriber: Subscriber<ViewState>, transform: StateTransform) {
//         subscriptions.forEach { subscription ->
//             if (subscription.subscriber == subscriber) {
//                 subscriptions.remove(subscriber)
//             }
//         }
//         val subscription = Subscription(subscriber, transform)
//         subscriptions.add(subscription)
//         subscription.updateState(oldState)
//     }
//
//     fun processIntents(intents: Flow<I>): Flow<I> = intents.onEach { viewIntents ->
//         intentsChannel.offer(viewIntents)
//     }
//
//     val processor: Flow<S> = intentsChannel.asFlow()
//         .flatMapLatest { action ->
//             intentProcessor.intentToResult(action)
//         }.scan(initialState) { previous, result ->
//             reducer.reduce(previous, result)
//         }.onEach { newState ->
//             subscriptions.forEach { subscription ->
//                 subscription.updateState(newState)
//             }
//             oldState = newState
//         }
// }
//
// internal class Subscription(
//     val subscriber: Subscriber<ViewState>,
//     private val transform: StateTransform
// ) {
//
//     private var oldState: ViewState? = null
//
//     fun updateState(state: ScreenState) {
//         val newState: ViewState = transform(state)
//         if (oldState == null || oldState != newState) {
//             subscriber.onNewState(newState)
//             oldState = newState
//         }
//     }
// }
