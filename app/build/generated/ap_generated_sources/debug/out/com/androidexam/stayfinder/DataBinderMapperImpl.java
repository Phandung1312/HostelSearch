package com.androidexam.stayfinder;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.androidexam.stayfinder.databinding.ActivityMainBindingImpl;
import com.androidexam.stayfinder.databinding.AdminHomeClassImpl;
import com.androidexam.stayfinder.databinding.ChatDetailClassImpl;
import com.androidexam.stayfinder.databinding.HomeClientImpl;
import com.androidexam.stayfinder.databinding.HomeDetailClientImpl;
import com.androidexam.stayfinder.databinding.ListChatClassImpl;
import com.androidexam.stayfinder.databinding.LoginClassImpl;
import com.androidexam.stayfinder.databinding.PostDetailAdminClassImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMAIN = 1;

  private static final int LAYOUT_FRAGMENTADMINHOME = 2;

  private static final int LAYOUT_FRAGMENTCHATDETAIL = 3;

  private static final int LAYOUT_FRAGMENTHOMECLIENT = 4;

  private static final int LAYOUT_FRAGMENTHOMEDETAILCLIENT = 5;

  private static final int LAYOUT_FRAGMENTLISTCHAT = 6;

  private static final int LAYOUT_FRAGMENTLOGIN = 7;

  private static final int LAYOUT_FRAGMENTPOSTDETAILADMIN = 8;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(8);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.androidexam.stayfinder.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.androidexam.stayfinder.R.layout.fragment_admin_home, LAYOUT_FRAGMENTADMINHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.androidexam.stayfinder.R.layout.fragment_chat_detail, LAYOUT_FRAGMENTCHATDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.androidexam.stayfinder.R.layout.fragment_home_client, LAYOUT_FRAGMENTHOMECLIENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.androidexam.stayfinder.R.layout.fragment_home_detail_client, LAYOUT_FRAGMENTHOMEDETAILCLIENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.androidexam.stayfinder.R.layout.fragment_list_chat, LAYOUT_FRAGMENTLISTCHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.androidexam.stayfinder.R.layout.fragment_login, LAYOUT_FRAGMENTLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.androidexam.stayfinder.R.layout.fragment_post_detail_admin, LAYOUT_FRAGMENTPOSTDETAILADMIN);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTADMINHOME: {
          if ("layout/fragment_admin_home_0".equals(tag)) {
            return new AdminHomeClassImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_admin_home is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCHATDETAIL: {
          if ("layout/fragment_chat_detail_0".equals(tag)) {
            return new ChatDetailClassImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_chat_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHOMECLIENT: {
          if ("layout/fragment_home_client_0".equals(tag)) {
            return new HomeClientImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home_client is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHOMEDETAILCLIENT: {
          if ("layout/fragment_home_detail_client_0".equals(tag)) {
            return new HomeDetailClientImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home_detail_client is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTLISTCHAT: {
          if ("layout/fragment_list_chat_0".equals(tag)) {
            return new ListChatClassImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_list_chat is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTLOGIN: {
          if ("layout/fragment_login_0".equals(tag)) {
            return new LoginClassImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_login is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPOSTDETAILADMIN: {
          if ("layout/fragment_post_detail_admin_0".equals(tag)) {
            return new PostDetailAdminClassImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_post_detail_admin is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(8);

    static {
      sKeys.put("layout/activity_main_0", com.androidexam.stayfinder.R.layout.activity_main);
      sKeys.put("layout/fragment_admin_home_0", com.androidexam.stayfinder.R.layout.fragment_admin_home);
      sKeys.put("layout/fragment_chat_detail_0", com.androidexam.stayfinder.R.layout.fragment_chat_detail);
      sKeys.put("layout/fragment_home_client_0", com.androidexam.stayfinder.R.layout.fragment_home_client);
      sKeys.put("layout/fragment_home_detail_client_0", com.androidexam.stayfinder.R.layout.fragment_home_detail_client);
      sKeys.put("layout/fragment_list_chat_0", com.androidexam.stayfinder.R.layout.fragment_list_chat);
      sKeys.put("layout/fragment_login_0", com.androidexam.stayfinder.R.layout.fragment_login);
      sKeys.put("layout/fragment_post_detail_admin_0", com.androidexam.stayfinder.R.layout.fragment_post_detail_admin);
    }
  }
}
