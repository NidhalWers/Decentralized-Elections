# Generated by Django 4.1.2 on 2022-11-23 10:39

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("AdminSpace", "0004_alter_election_electionapikey"),
    ]

    operations = [
        migrations.AddField(
            model_name="election",
            name="ElectionCandidate",
            field=models.ManyToManyField(
                blank=True, null=True, to="AdminSpace.candidate"
            ),
        ),
    ]