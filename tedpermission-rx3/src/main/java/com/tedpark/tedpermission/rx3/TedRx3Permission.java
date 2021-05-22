package com.tedpark.tedpermission.rx3;

import android.content.Context;

import com.gun0912.tedpermission.PermissionBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermissionBase;
import com.gun0912.tedpermission.TedPermissionResult;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public class TedRx3Permission extends TedPermissionBase {

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder extends PermissionBuilder<Builder> {

        private Builder(Context context) {
            super(context);
        }

        public Single<TedPermissionResult> request() {
            return Single.create(emitter -> {
                PermissionListener listener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        emitter.onSuccess(new TedPermissionResult(null));
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        emitter.onSuccess(new TedPermissionResult(deniedPermissions));
                    }
                };

                try {
                    setPermissionListener(listener);
                    checkPermissions();
                } catch (Exception exception) {
                    emitter.onError(exception);
                }
            });
        }
    }
}
