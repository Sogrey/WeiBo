/*
 * Copyright (c) 2016 Sogrey [https://github.com/Sogrey/WeiBo].
 * IDE:Android Studio 2.1.1 + JDK 1.8
 * QQ:408270653
 * 仅供学习交流
 * Copyright (c) Sogrey, All rights reserved.
 */

package org.sogrey.weibo.pro.modular.base.view.toolsBar.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.sogrey.weibo.R;

/**
 * 默认样式的Toolsbar
 * Created by Sogrey on 2016/6/13.
 */
public class DefaultToolsbar extends AbsToolsBar<DefaultToolsbar.Builder.DefualtToolsbarParams> {

    protected DefaultToolsbar(DefaultToolsbar.Builder.DefualtToolsbarParams param) {
        super(param);
    }

    @Override
    public int getLayoutId() {
        return R.layout.toolsbar_default;
    }

    @Override
    public void createAndBind() {
        super.createAndBind();
        setImage(R.id.img_toolsbar_defaulkt_back,getParam().backId,getParam().onBackClickListener);
        setImage(R.id.img_toolsbar_defaulkt_icon,getParam().iconId,getParam().onIconClickListener);
        setText(R.id.txt_toolsbar_defualt_title,getParam().titleText,getParam().titleTextColor,
                getParam().onTitleClickListener
        );
        //        addOperaViews(R.id.lyt_toolsbar_opera,getParam().operaViews,getParam().);
    }

    //    protected void addOperaViews(int rootId,View operaViews) {
    //        View view=findViewById(rootId);
    //        if (view==null||!(view instanceof LinearLayout))
    //            return;
    //        getParam().operaViewRoot=(LinearLayout)view;
    //        if (getParam().operaViewRoot!=null) {
    //            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
    //                    LayoutParams.WRAP_CONTENT,
    //                    LayoutParams.MATCH_PARENT);
    //            getParam().setOperaOnItemClickListener(operaViews.getId());
    //
    //            getParam().operaViewRoot.addView(operaViews,lp);
    //        }
    //    }

    protected void setImage(int viewId,int resId,View.OnClickListener l) {
        View view=findViewById(viewId);
        if (view==null||!(view instanceof ImageView))
            return;
        ImageView imageView=(ImageView)view;
        if (resId<=0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(resId);
            imageView.setOnClickListener(l);
        }
    }

    protected void setText(int viewId,int textId,View.OnClickListener l) {
        setText(viewId,getString(textId),0,l);
    }

    protected void setText(int viewId,String text,View.OnClickListener l) {
        setText(viewId,text,0,l);
    }

    protected void setText(int viewId,int textId,int textColor,View.OnClickListener l) {
        setText(viewId,getString(textId),textColor,l);
    }

    protected void setText(int viewId,String text,int textColor,View.OnClickListener l) {
        View view=findViewById(viewId);
        if (view==null||!(view instanceof TextView))
            return;
        TextView textView=(TextView)view;
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
            if (textColor!=0) {
                textView.setTextColor(getColor(textColor));
            }
            textView.setOnClickListener(l);
        }
    }


    public static class Builder extends AbsToolsBar.Builder {

        public  int                   iconId;
        public  View                  operaView;
        public  View.OnClickListener  onIconClickListener;
        private DefualtToolsbarParams params;

        public Builder(Context context,ViewGroup parent) {
            params=new DefualtToolsbarParams(context,parent);
        }

        public DefaultToolsbar.Builder setBackId(int backId) {
            this.params.backId=backId;
            return this;
        }

        public DefaultToolsbar.Builder setIconId(int iconId) {
            this.params.iconId=iconId;
            return this;
        }

        public DefaultToolsbar.Builder setTitleText(int titleText) {
            this.params.titleText=this.params.getString(titleText);
            return this;
        }

        public DefaultToolsbar.Builder setTitleText(String titleText) {
            this.params.titleText=titleText;
            return this;
        }

        public DefaultToolsbar.Builder setTitleTextColor(int titleTextColor) {
            this.params.titleTextColor=titleTextColor;
            return this;
        }

        public DefaultToolsbar.Builder setBackClickListener(View.OnClickListener l) {
            this.params.onBackClickListener=l;
            return this;
        }

        public DefaultToolsbar.Builder setIconClickListener(View.OnClickListener l) {
            this.params.onIconClickListener=l;
            return this;
        }

        public DefaultToolsbar.Builder setTitleClickListener(View.OnClickListener l) {
            this.params.onTitleClickListener=l;
            return this;
        }

        public DefaultToolsbar.Builder setOperaItemClickListener(
                int viewId,View.OnClickListener
                l
        ) {
            View view=this.params.findViewById(viewId);
            if (view!=null) {
                view.setOnClickListener(l);
            }
            return this;
        }

        public DefaultToolsbar.Builder addViewOIntoOperaView(
                View v
        ) {
            this.params.operaViews=v;
            return this;
        }

        @Override
        public AbsToolsBar create() {
            DefaultToolsbar defaultToolsbar=new DefaultToolsbar(params);
            defaultToolsbar.createAndBind();
            return defaultToolsbar;
        }

        public static class DefualtToolsbarParams extends AbsToolsBar.Builder.ToolsBarParam {

            public int                  backId;
            public int                  iconId;
            public String               titleText;
            public int                  titleTextColor;
            public LinearLayout         operaViewRoot;
            public View                 operaViews;
            public View.OnClickListener onBackClickListener;
            public View.OnClickListener onIconClickListener;
            public View.OnClickListener onTitleClickListener;

            public DefualtToolsbarParams(Context context,ViewGroup parent) {
                super(context,parent);
            }

            public String getString(int resId) {
                return context.getResources().getString(resId);
            }

            public View findViewById(@NonNull int resId) {
                if (operaViewRoot!=null) {
                    return operaViewRoot.findViewById(resId);
                }
                return null;
            }

            //            public View getOperaView{
            //                return this.operaView;
            //            }
            public void setOperaOnItemClickListener(int resId,View.OnClickListener l) {
                View view=findViewById(resId);
                if (view!=null) {
                    view.setOnClickListener(l);
                }
            }
        }
    }
}
