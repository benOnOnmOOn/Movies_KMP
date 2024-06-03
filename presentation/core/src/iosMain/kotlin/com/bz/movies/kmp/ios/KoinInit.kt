import com.bz.movies.kmp.di.presentationModule
import org.koin.core.context.startKoin

fun InitKoin(){
    startKoin {
        modules(presentationModule)
    }
}
