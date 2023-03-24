package view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccountLoginPage(block: ((String, String) -> Unit)? = null) {
    var account by remember {
        mutableStateOf("")
    }
    var psd by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(text = "企迈智慧餐饮解决方案", fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = account,
            label = { Text(text = "账号", fontSize = 19.sp) },
            leadingIcon = {
                Icon(
                    painter = painterResource("images/login_et_user.webp"),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                account = it
            },
            modifier = Modifier.width(400.dp),
            textStyle = TextStyle(fontSize = 19.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                focusedLabelColor = MaterialTheme.colors.primary,
                cursorColor = MaterialTheme.colors.primary
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = psd,
            label = { Text(text = "密码", fontSize = 19.sp) },
            leadingIcon = {
                Icon(
                    painter = painterResource("images/login_et_psw.webp"),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                psd = it
            },
            modifier = Modifier.width(400.dp),
            textStyle = TextStyle(fontSize = 19.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                focusedLabelColor = MaterialTheme.colors.primary,
                cursorColor = MaterialTheme.colors.primary
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(modifier = Modifier
            .width(400.dp)
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            onClick = {
                block?.invoke(account, psd)
            }) {
            Text(text = "立即登录", color = Color.White)
        }
    }
}

