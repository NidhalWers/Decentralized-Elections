# Generated by Django 4.1.2 on 2022-11-08 15:35

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("AdminSpace", "0002_alter_candidate_candidatedescription_and_more"),
    ]

    operations = [
        migrations.AlterField(
            model_name="candidate",
            name="CandidateImage",
            field=models.ImageField(blank=True, upload_to="CandidateImage"),
        ),
        migrations.AlterField(
            model_name="candidate",
            name="CandidateProgram",
            field=models.FileField(blank=True, upload_to="CandidateProgram"),
        ),
    ]
