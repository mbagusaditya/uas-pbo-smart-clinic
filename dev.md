# Catatan developer

## Backend

### LoginService

untuk menghubungkan dengan LoginService, cukup panggil 
```java
LoginService.login(String username, String password);
```

Parameter yang harus diisikan
```json
{
  "username": "string",
  "password": "string"
}
```

fungsi login akan mengembalikan true jika user ditemukan dan berhasil login. dan mengembalikan false jika user tidak ditemukan
