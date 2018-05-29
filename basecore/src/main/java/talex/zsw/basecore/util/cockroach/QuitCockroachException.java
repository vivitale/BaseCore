package talex.zsw.basecore.util.cockroach;

final class QuitCockroachException extends RuntimeException
{
	public QuitCockroachException(String message)
	{
		super(message);
	}
}
