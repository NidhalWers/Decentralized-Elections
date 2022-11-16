from Connect.models import Citizen
from django.db.models import Q
from django.contrib.auth.backends import ModelBackend


class AuthBackend(ModelBackend):
    # supports_object_permissions = True
    # supports_anonymous_user = False
    # supports_inactive_user = False


    def get_user(self, user_id):
       try:
          return Citizen.objects.get(pk=user_id)
       except Citizen.DoesNotExist:
          return None


    def authenticate(self, request, **kwargs):
        try:
            user = Citizen.objects.get(
                fiscal_number=kwargs['id_connect']
            )
        except Citizen.DoesNotExist:
            # return None
            try:
                user = Citizen.objects.get(
                sick_security_number=kwargs['id_connect']
            )
            except Citizen.DoesNotExist:
                return None

        return user if user.check_password(kwargs['password']) else None