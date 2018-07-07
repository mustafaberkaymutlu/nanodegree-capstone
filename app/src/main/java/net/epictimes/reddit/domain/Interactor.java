package net.epictimes.reddit.domain;

import android.support.annotation.NonNull;

import javax.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface Interactor extends UseCase {

    /**
     * Sends changes to data layer.
     * Returns a {@link Single} that will emit the result of the send operation.
     */
    interface SendInteractor<Params, Result> extends Interactor {

        @NonNull
        Single<Result> getSingle(@Nullable final Params params);

    }

    /**
     * Retrieves changes from the data layer.
     * It returns a {@link Flowable} that emits updates for the retrieved object.
     * The returned {@link Flowable} will never complete, but it can error if there are any
     * problems performing the required actions to serve the data.
     */
    interface RetrieveInteractor<Params, Object> extends Interactor {

        @NonNull
        Flowable<Object> getBehaviorStream(@Nullable final Params params);

    }

    /**
     * The request interactor is used to request some result once.
     * The returned observable is a single, emits once and then completes or errors.
     */
    interface RequestInteractor<Params, Result> extends Interactor {

        @NonNull
        Single<Result> getSingle(@Nullable final Params params);

    }

}
