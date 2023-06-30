package sg.edu.np.mad.happyhabit;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyDeviceAdminReciever extends DeviceAdminReceiver
{   @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "admin app", Toast.LENGTH_SHORT).show();
    }

    private void showToast(Context context, String string) {

    }
}
