package RSA;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

public class RSA extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnRandom;
	private JButton btnEncrypt;
	private JButton btnDecrypt;
	private JTextField txtQ;
	private JTextField txtP;
	private JTextField txtN;
	private JTextField txtD;
	private JTextField txtE;
	private JTextField txtM;
	private JTextField txtPlaintext1;
	private JLabel lblPlaintext;
	private JTextField txtEncryptText;
	private JLabel lblEncryptText;
	private JLabel lblDecryptText;
	private JTextField txtPlainText2;
	
	BigInteger p, q, N, r, E, D;
	// Khai báo 2 số nguyên tố ngẫu nhiên p, q
	// N = p * q
	// r = (q-1)(p-1)
	// E tượng trưng cho khóa công khai còn D là khóa riêng tư
	private String plainText = ""; //Chuỗi để lưu văn bản gốc cần mã hóa.
	private BigInteger[] encryptText = new BigInteger[100000]; //Mảng BigInteger để lưu dữ liệu đã được mã hóa
	

	public RSA() {
		setTitle("Nhóm 4-RSA");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 994, 392);
		
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "RSA-algorithm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 960, 336);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnRandom = new JButton("Random");
		btnRandom.setBounds(59, 268, 85, 21);
		panel.add(btnRandom);
		
		btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(157, 269, 85, 21);
		panel.add(btnEncrypt);
		
		btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(257, 268, 85, 21);
		panel.add(btnDecrypt);
		
		txtQ = new JTextField();
		txtQ.setBounds(49, 24, 96, 19);
		panel.add(txtQ);
		txtQ.setColumns(10);
		
		JLabel lblq = new JLabel("q:");
		lblq.setBounds(33, 27, 45, 13);
		panel.add(lblq);
		
		txtP = new JTextField();
		txtP.setBounds(49, 53, 96, 19);
		panel.add(txtP);
		txtP.setColumns(10);
		
		JLabel lblp = new JLabel("p:");
		lblp.setBounds(33, 53, 45, 13);
		panel.add(lblp);
		
		txtN = new JTextField();
		txtN.setBounds(48, 82, 96, 19);
		panel.add(txtN);
		txtN.setColumns(10);
		
		JLabel lblN = new JLabel("N:");
		lblN.setBounds(33, 85, 45, 13);
		panel.add(lblN);
		
		txtD = new JTextField();
		txtD.setBounds(197, 82, 96, 19);
		panel.add(txtD);
		txtD.setColumns(10);
		
		JLabel lblD = new JLabel("D:");
		lblD.setBounds(177, 85, 45, 13);
		panel.add(lblD);
		
		txtE = new JTextField();
		txtE.setBounds(197, 24, 96, 19);
		panel.add(txtE);
		txtE.setColumns(10);
		
		JLabel lblE = new JLabel("e:");
		lblE.setBounds(177, 27, 45, 13);
		panel.add(lblE);
		
		txtM = new JTextField();
		txtM.setBounds(197, 53, 96, 19);
		panel.add(txtM);
		txtM.setColumns(10);
		
		JLabel lblM = new JLabel("M:");
		lblM.setBounds(177, 56, 45, 13);
		panel.add(lblM);
		
		txtPlaintext1 = new JTextField();
		txtPlaintext1.setBounds(117, 150, 817, 19);
		panel.add(txtPlaintext1);
		txtPlaintext1.setColumns(10);
		
		lblPlaintext = new JLabel("Plaintext:");
		lblPlaintext.setBounds(23, 153, 75, 13);
		panel.add(lblPlaintext);
		
		txtEncryptText = new JTextField();
		txtEncryptText.setBounds(117, 189, 817, 19);
		panel.add(txtEncryptText);
		txtEncryptText.setColumns(10);
		
		lblEncryptText = new JLabel("EncryptText:");
		lblEncryptText.setBounds(23, 192, 85, 13);
		panel.add(lblEncryptText);
		
		lblDecryptText = new JLabel("DecryptText");
		lblDecryptText.setBounds(23, 226, 75, 21);
		panel.add(lblDecryptText);
		
		txtPlainText2 = new JTextField();
		txtPlainText2.setBounds(117, 227, 817, 19);
		panel.add(txtPlainText2);
		txtPlainText2.setColumns(10);
		btnDecrypt.addActionListener(this);
		btnEncrypt.addActionListener(this);
		
		btnRandom.addActionListener(this);

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnRandom)){
			//btnRandom
			int primeSize = 8; 	//Độ dài khóa
			//random số nguyên tố p,q
			p = BigInteger.probablePrime(primeSize, new Random());
			do{
				q = BigInteger.probablePrime(primeSize, new Random());
			}while (q.compareTo(p)==0);

			//N = p * q
			N = p.multiply(q); // hàm nhân 2 số
			//r = (q - 1)(p - 1)
			r = p.subtract(BigInteger.valueOf(1)); //hàm trừ 2 số
			r = r.multiply(q.subtract(BigInteger.valueOf(1))); 
			//Chọn E nhỏ hơn r
			do{
				E = new BigInteger(2*primeSize, new Random()); // Tạo số E ngẫu nhiên đến khi tìm thấy E nhỏ hơn r
			}while((E.compareTo(r)!= -1) || (E.gcd(r).compareTo(BigInteger.valueOf(1))!=0)); //E và r có ước chung lớn nhất là 1
			// D bằng nghịch đảo của E trong mod r
			D = E.modInverse(r);
	
			txtP.setText(p.toString());
			txtQ.setText(q.toString());
			txtN.setText(N.toString());
			txtM.setText(r.toString());
			txtE.setText(E.toString());
			txtD.setText(D.toString());	
		}
		else if(o.equals(btnEncrypt)){
			plainText = txtPlaintext1.getText();
			//Mã hóa thông điệp
			encryptText = encrypt(plainText);
			String encryptText1 = ""; //tạo 1 String lưu dữ liệu mã hóa
			for(int i =0; i < encryptText.length; i++){      //Cho chạy hết chuỗi và truyền dữ liệu vào String
				encryptText1 += encryptText[i];
				if(i != encryptText.length - 1)
					System.out.print("");
			}
			txtEncryptText.setText(encryptText1);
		}
		else if(o.equals(btnDecrypt)){
			String decryptText = decrypt(encryptText,D,N);
			txtPlainText2.setText(decryptText);
		}
	}

	// Chuyển đổi chuỗi văn bản thành một mảng các số BigInteger (mỗi số biểu diễn một ký tự)
	public BigInteger[] encrypt(String message){
		int i;
		byte[] temp = new byte[1];
		// Chứa chuỗi nhập
		// chuyển đổi chuỗi văn bản message thành một mảng các byte bằng cách sử dụng phương thức getBytes()
		// Mảng digits bây giờ chứa các byte tương ứng với các ký tự trong chuỗi văn bản.
		// Mảng bigdigits được khởi tạo với kích thước bằng độ dài của mảng digits, sẽ chứa các số BigInteger đại diện cho từng byte
		byte[] digits = message.getBytes(); //
		BigInteger[] bigdigits = new BigInteger[digits.length];

		//chuyển đổi byte thành số int 
		// Vòng lặp này chuyển đổi từng byte trong mảng digits thành một số BigInteger tương ứng.
		// Biến temp là một mảng byte có độ dài 1, được sử dụng để chứa byte hiện tại cần chuyển đổi.
		// Mỗi byte được chứa trong mảng temp và sau đó được tạo thành một số BigInteger.
		// Số BigInteger này được lưu vào mảng bigdigits.
		for(i = 0; i < bigdigits.length; i++){
			temp[0] = digits[i];
			bigdigits[i] = new BigInteger(temp);
		}
		BigInteger[] encrypted = new BigInteger[bigdigits.length];

		// Vòng lặp này duyệt qua từng số BigInteger trong mảng bigdigits.
		// Mỗi số BigInteger này sau đó được mã hóa bằng cách sử dụng phép lũy thừa
		// với giá trị khóa công khai (E, N) của thuật toán RSA.
		// Kết quả của phép lũy thừa, sau khi lấy phần dư khi chia cho N, được gán vào mảng encrypted,
		// tạo ra một mảng các số BigInteger đã được mã hóa.
		for(i = 0; i < bigdigits.length; i++)
			encrypted[i] = bigdigits[i].modPow(E, N);

		//trả về mảng encrypted chứa các số BigInteger đã được mã hóa, mỗi số biểu diễn một ký tự trong chuỗi văn bản ban đầu
		return encrypted;
	}

	//hàm giải mã

	public String decrypt(BigInteger[] encrypted, BigInteger D, BigInteger N) {
		int i;
		BigInteger[] decrypted = new BigInteger[encrypted.length];
		for (i = 0; i < decrypted.length; i++) 
			decrypted[i] = encrypted[i].modPow(D, N);

		char[] charArray = new char[decrypted.length];

		for (i = 0; i < charArray.length; i++) 
			charArray[i] = (char) decrypted[i].intValue();

		return (new String(charArray));
	}
}