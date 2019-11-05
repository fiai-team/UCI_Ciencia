package cu.uci.fiai.uciencia.transformations;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Tyto on 26/6/2018.
 */

public class FadeOutTransformation implements ViewPager.PageTransformer{

    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX(-position*page.getWidth());
        page.setAlpha(1-Math.abs(position));
    }

}
