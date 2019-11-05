package cu.uci.fiai.uciencia.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements OnBMClickListener {

    private static final int CALL_PERMISSION_REQUEST = 0;

    private static final int CALL_PERMISSION_REQUEST_1 = 1;
    private static final int CALL_PERMISSION_REQUEST_2 = 2;

    private static final int CALL_1 = 0;
    private static final int CALL_2 = 1;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.nav_drawer_1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        BoomMenuButton boomButton = (BoomMenuButton) view.findViewById(R.id.bmbContacts);
        assert boomButton != null;

        boomButton.setNormalColor(getResources().getColor(R.color.colorPrimaryDark));
        boomButton.setHighlightedColor(getResources()
                .getColor(R.color.colorPrimaryLight));
        boomButton.setButtonEnum(ButtonEnum.Ham);
        boomButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        boomButton.setButtonPlaceEnum(ButtonPlaceEnum.Vertical);
        boomButton.setPieceCornerRadius(Util.dp2px(getResources()
                .getDimension(R.dimen._2sdp)));
        boomButton.setBoomEnum(BoomEnum.RANDOM);
        boomButton.setDotRadius(0);
        boomButton.addBuilder(new HamButton.Builder()
                .listener(this)
                .pieceColor(Color.parseColor("#00000000"))
                .normalImageRes(R.drawable.ic_send_email)
                .normalColorRes(R.color.md_indigo_900)
                .normalText(getString(R.string.uciencia_mail))
                .textSize(24));
        boomButton.addBuilder(new HamButton.Builder()
                .listener(this)
                .pieceColor(Color.parseColor("#00000000"))
                .normalImageRes(R.drawable.ic_open_website)
                .normalColorRes(R.color.md_indigo_800)
                .normalText(getString(R.string.uciencia_web).replace("https://", ""))
                .textSize(24));
        boomButton.addBuilder(new HamButton.Builder()
                .listener(this)
                .pieceColor(Color.parseColor("#00000000"))
                .normalImageRes(R.drawable.ic_call)
                .normalColorRes(R.color.md_indigo_700)
                .normalText(getString(R.string.uciencia_phone_1))
                .textSize(24));
        boomButton.addBuilder(new HamButton.Builder()
                .listener(this)
                .pieceColor(Color.parseColor("#00000000"))
                .normalImageRes(R.drawable.ic_call)
                .normalColorRes(R.color.md_indigo_600)
                .normalText(getString(R.string.uciencia_phone_2))
                .textSize(24));

        return view;
    }

    @Override
    public void onBoomButtonClick(int index) {
        switch (index) {
            case 0:
                sendMail();
                break;
            case 1:
                openWebsite();
                break;
            case 2:
                checkCallPermission(CALL_PERMISSION_REQUEST_1);
                break;
            case 3:
                checkCallPermission(CALL_PERMISSION_REQUEST_2);
                break;
        }

        Utils.vibrate(getContext());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (CALL_PERMISSION_REQUEST_1 == requestCode
                || CALL_PERMISSION_REQUEST_2 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callToUCIENCIA(requestCode);
            } else {
                Toast.makeText(getContext(), getString(R.string.string_cannot_calling),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void checkCallPermission(int call){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasCallPhonePermission = getContext().checkSelfPermission(Manifest.permission.CALL_PHONE);;

            if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.CALL_PHONE},
                        call);
            } else {
                callToUCIENCIA(call);
            }
        } else {
            callToUCIENCIA(call);
        }
    }

    private void callToUCIENCIA(int call) {
        if (call == CALL_PERMISSION_REQUEST_1) {
            callUCIENCIA1();
        } else if (call == CALL_PERMISSION_REQUEST_2) {
            callUCIENCIA2();
        }
    }

    private void callUCIENCIA1() {
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + getString(R.string.uciencia_phone_1)));

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Toast.makeText(getContext(), getString(R.string.string_calling),
                Toast.LENGTH_LONG).show();
        startActivity(phoneIntent);
    }

    private void callUCIENCIA2() {
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + getString(R.string.uciencia_phone_2)));

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Toast.makeText(getContext(), getString(R.string.string_calling),
                Toast.LENGTH_LONG).show();
        startActivity(phoneIntent);
    }

    private void openWebsite() {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.uciencia_web))));
    }

    private void sendMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.uciencia_mail)});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.string_to_uciencia));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.string_send_email_with)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), getString(R.string.string_cannot_send_email),
                    Toast.LENGTH_LONG).show();
        }
    }

}
