from django.urls import path, include
from django.conf import settings
from django.contrib.auth import views as auth_views

from Connect import views

urlpatterns = [
    path('login/franceconnect', views.fc_html, name='fc'),
    path('login/franceconnect/impotsgouvfr', views.login_impots, name='login_impots'),
    path('login/franceconnect/fcamelifr', views.login_ameli, name='login_ameli'),
    path('logout/', auth_views.LogoutView.as_view(next_page=settings.LOGOUT_REDIRECT_URL), name='logout'),
    path('account/', views.account_html, name='account'),
    path('api/getUserInfos/<int:pk>', views.get_user_infos, name='get_user_infos'),
]