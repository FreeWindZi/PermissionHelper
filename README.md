#PermissionHelper

##项目介绍：
    PermissionHelper是一个android的项目库，用于解决android权限问题，为不同的android版本提供统一接口。

##下载
###Gradle

    compile 'com.navy.chen:permission-helper:0.3'

###注意事项：

1. 必须在Activity或者Fragment中使用。

2. 必须重写Activity或者Fragment的onRequestPermissionsResult方法，或者实现ActivityCompat.OnRequestPermissionsResultCallback接口。
    推荐第二种。
3. 在的`onRequestPermissionsResult`方法中调用 `PermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);`

4. 如果你实现的接口是`PermissionDetailCallback`接口，它和`PermissionCallback`接口不同之处，提供了 `onPermissionExplained `权限解释接口。 `onPermissionExplained
 `调用`PermissionHelper.getInstance().requestAfterExplanation(permission)`;

##使用

PermissionCallback接口

     PermissionHelper.getInstance().with(this)
        .setPermissions(PermissionModel.READ_EXTERNAL_STORAGE)
        .setRequestCode(1000)
        .setForceAccepting(true) //表示必须强制用户接受该权限， 默认为false
        .setPermissonCallback(new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionReject() {

                    }


                })
        .requestPermissions();

PermissionDetailCallback接口

    PermissionHelper.getInstance().with(this)
                .setPermissions(PermissionModel.READ_EXTERNAL_STORAGE,
                        PermissionModel.WRITE_EXTERNAL_STORAGE,
                        PermissionModel.ACCESS_COARSE_LOCATION,
                        PermissionModel.WRITE_CONTACTS,
                        PermissionModel.CAMERA,
                        PermissionModel.READ_CONTACTS)
                .setRequestCode(1000)
                .setPermissonCallback(new PermissionDetailCallback() {

                    @Override
                    public void onPermissionExplained(String[] permission) {

                       //显示ui  告诉用户为什要使用该权限，根据用户行为调用下面的方法
                         PermissionHelper.getInstance().requestAfterExplanation(permission);
                    }

                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionReject() {

                    }


                })
                .requestPermissions();

onRequestPermissionsResult重写

        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }