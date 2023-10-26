# Semestral work - Server (Social_network)

> Dotaz navíc: Zobrazení celkoveho počtu liků co uzivatel získal

> Koplexní operace: Spolu autorství. Uživatel vytvorí prispevek a muze nastavit dalsiho uzivatele jako autora, takže se
se vytvoří post u dalsiho uzivatele (Pouze pratele).

## Model:

<img src="resources_readme/img.png" alt="Model">

zkouseni bez client:

vget

curl

HTTP client -- intel idea 
priklad requestu:
POST /api/v1/user/HTTP/1.1
Host: piskvorky.jobs.cz
Contntent-Type: application/jason

{
    "nicname": "string",
    "email": "String"
}


dokumentace: openapi
(swagger editor)