package apps.mjn.giphyapp.mvp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Created by mJafarinejad on 8/11/2018.
 */
abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    lateinit var view: WeakReference<V?>
    private val compositeDisposable = CompositeDisposable()

    override fun attach(view: V) {
        this.view = WeakReference(view)
    }

    override fun detach() {
        view.clear()
        compositeDisposable.dispose()
    }

    fun getView(): V? = view.get()

    /**
     * Adds a 'displosable' to local list of disposables ('compositeDisposable')
     */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}