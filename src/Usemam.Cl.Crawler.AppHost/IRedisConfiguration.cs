namespace Usemam.Cl.Crawler.AppHost
{
    public interface IRedisConfiguration
    {
        IRedisConfiguration WithHostInfo(string host, int port);

        IRedisConfiguration WithHostInfoFromArgs(string[] args);
    }
}