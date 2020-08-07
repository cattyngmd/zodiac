package cat.yoink.zodiac;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;

public class HWID
{
	public static boolean check( ) // эту функцию вызывай, если true то хвид нормальный, если false то ХУЕВЫЙ
	{
		String hwid = getHWID( );
		if( hwid.equals( "UNKNOWN_OS" ) || hwid.equals( "ERROR" ) || !check( hwid ) )
		{
			Object[ ] options = { "Close", "Close + copy HWID" };
			String str = "Authentication failed\nCouldn't find your HWID in the database\nYour HWID is " + hwid;
			int n = JOptionPane.showOptionDialog( null, str, null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 1 ] );
			if( n == 1 )
			{
				Toolkit.getDefaultToolkit( )
					   .getSystemClipboard( )
					   .setContents( new StringSelection( hwid ), null );
			}
			
			net.minecraftforge.fml.common.FMLCommonHandler.instance( ).exitJava( 1, true );
			net.minecraftforge.fml.common.FMLCommonHandler.instance( ).exitJava( 0, true );
                        return false;
		}
                return true;
	}
	
	private static boolean check( String hwid )
	{
		try
		{
			StringBuilder sb = new StringBuilder( );
			URL url = new URL( "https://pastebin.com/GkEPRNrf" );
			HttpURLConnection connection = ( HttpURLConnection )url.openConnection( );
			connection.setRequestProperty(
					"User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11" );
			connection.setRequestMethod( "GET" );
			
			BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream( ) ) );
			
			String line;
			while( ( line = reader.readLine( ) ) != null )
				sb.append( line );
			
			reader.close( );
			connection.disconnect( );
			
			return sb.toString( ).toUpperCase( ).contains( hwid );
		}
		catch( Exception e )
		{
			
		}
		
		return false;
	}

	private static String getHWID( )
	{
		String lowercaseos = System.getProperty( "os.name" ).toLowerCase( );
		if( lowercaseos.contains( "windows" ) )
		{
			try
			{
				Process var0 = Runtime.getRuntime( ).exec( "wmic baseboard get product,Manufacturer,version,serialnumber" );
				Scanner scanner = new Scanner( var0.getInputStream( ) );
				scanner.nextLine( );
				scanner.nextLine( );
				String hwid = scanner.nextLine( );
				scanner.close( );
				
				hwid = hwid.replaceAll( " ", "" );
				
				MessageDigest md = MessageDigest.getInstance( "MD5" );
				md.update( hwid.getBytes( ) );
				
				return DatatypeConverter.printHexBinary( md.digest( ) ).toUpperCase( );
			}
			catch( Exception e )
			{
				e.printStackTrace( );
				return "ERROR";
			}
		}
		else if( lowercaseos.startsWith( "nix" ) ||
			lowercaseos.startsWith( "nux" ) ||
			lowercaseos.startsWith( "aix" ) ||
			lowercaseos.startsWith( "mac" ) ) // :thinking:
		{
			try
			{
				Process var0 = Runtime.getRuntime( ).exec( "cat /sys/class/dmi/id/product_uuid" );
				Scanner scanner = new Scanner( var0.getInputStream( ) );
				String hwid = "";
				String line = null;
				while( ( line = scanner.nextLine( ) ) != null )
					hwid += line;
				
				hwid = hwid.replaceAll( " ", "" );
				
				MessageDigest md = MessageDigest.getInstance( "MD5" );
				md.update( hwid.getBytes( ) );
				
				return DatatypeConverter.printHexBinary( md.digest( ) ).toUpperCase( );
			}
			catch( Exception e )
			{
				e.printStackTrace( );
				return "ERROR";
			}
		}
		
		return "UNKNOWN_OS";
	}
}
