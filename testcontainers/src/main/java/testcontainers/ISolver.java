package testcontainers;

public interface ISolver<A, B, R> {
	
	IResult<R> solve(IArgument<A, B> arg);

}
