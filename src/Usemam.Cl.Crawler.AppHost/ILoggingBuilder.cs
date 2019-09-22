namespace Usemam.Cl.Crawler.AppHost
{
    public interface ILoggingBuilder
    {
        ILoggingBuilder LoggerFor<T>();
    }
}