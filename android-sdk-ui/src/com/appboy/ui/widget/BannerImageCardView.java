package com.appboy.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.appboy.Appboy;
import com.appboy.Constants;
import com.appboy.models.cards.BannerImageCard;
import com.appboy.ui.R;
import com.appboy.ui.actions.IAction;
import com.appboy.ui.actions.WebAction;
import com.appboy.ui.support.StringUtils;

public class BannerImageCardView  extends BaseCardView<BannerImageCard> {
  private static final String TAG = String.format("%s.%s", Constants.APPBOY, BannerImageCardView.class.getName());

  private final ImageView mImage;
  private IAction mCardAction;

  public BannerImageCardView(Context context) {
    this(context, null);
  }

  public BannerImageCardView(final Context context, BannerImageCard card) {
    super(context);
    mImage = (ImageView) findViewById(R.id.com_appboy_banner_image_card_image);

    if (card != null) {
      setCard(card);
    }

    safeSetBackground(getResources().getDrawable(R.drawable.com_appboy_card_background));
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.com_appboy_banner_image_card;
  }

  @Override
  public void onSetCard(final BannerImageCard card) {
    setImageViewToUrl(mImage, card.getImageUrl(), 6f);

    if (!StringUtils.isNullOrBlank(card.getUrl())) {
      mCardAction = new WebAction(card.getUrl());
    } else {
      mCardAction = null;
    }

    setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mCardAction != null) {
          Appboy.getInstance(mContext).logFeedCardClick(card.getId());
          mCardAction.execute(mContext);
        }
      }
    });
  }
}