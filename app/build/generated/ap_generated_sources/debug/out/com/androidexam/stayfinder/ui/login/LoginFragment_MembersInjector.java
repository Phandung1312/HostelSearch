// Generated by Dagger (https://dagger.dev).
package com.androidexam.stayfinder.ui.login;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class LoginFragment_MembersInjector implements MembersInjector<LoginFragment> {
  private final Provider<GoogleSignInClient> gscProvider;

  private final Provider<FirebaseAuth> authProvider;

  public LoginFragment_MembersInjector(Provider<GoogleSignInClient> gscProvider,
      Provider<FirebaseAuth> authProvider) {
    this.gscProvider = gscProvider;
    this.authProvider = authProvider;
  }

  public static MembersInjector<LoginFragment> create(Provider<GoogleSignInClient> gscProvider,
      Provider<FirebaseAuth> authProvider) {
    return new LoginFragment_MembersInjector(gscProvider, authProvider);
  }

  @Override
  public void injectMembers(LoginFragment instance) {
    injectGsc(instance, gscProvider.get());
    injectAuth(instance, authProvider.get());
  }

  @InjectedFieldSignature("com.androidexam.stayfinder.ui.login.LoginFragment.gsc")
  public static void injectGsc(LoginFragment instance, GoogleSignInClient gsc) {
    instance.gsc = gsc;
  }

  @InjectedFieldSignature("com.androidexam.stayfinder.ui.login.LoginFragment.auth")
  public static void injectAuth(LoginFragment instance, FirebaseAuth auth) {
    instance.auth = auth;
  }
}
