from rest_framework import routers,serializers,viewsets
from .models import Citizen

class CitizenSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Citizen
        fields = ['id','username','email','first_name','last_name','gender','city','phone','postal_code','street_name','street_number','fiscal_number','sick_security_number','birth_date']